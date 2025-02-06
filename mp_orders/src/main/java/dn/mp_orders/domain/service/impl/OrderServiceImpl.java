
package dn.mp_orders.domain.service.impl;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dn.mp_orders.api.client.WarehouseHttpClient;
import dn.mp_orders.domain.event.KafkaEvent;
import dn.mp_orders.api.dto.ListOrderDto;
import dn.mp_orders.api.dto.OrderDto;
import dn.mp_orders.api.client.WarehouseResponse;
import dn.mp_orders.api.mapper.OrderMapper;
import dn.mp_orders.domain.entity.OrderEntity;
import dn.mp_orders.api.exception.OrderNotFound;
import dn.mp_orders.domain.event.OrderSavedEvent;
import dn.mp_orders.domain.repository.OrderRepository;
import dn.mp_orders.domain.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
@CacheConfig(cacheManager = "cacheManager")
public class OrderServiceImpl implements OrderService {

    private static final String DELIVERED = "ДОСТАВЛЕН";

    private static final String ON_THE_WAY = "В ПУТИ";

    private static final String PROCESSING = "В ОБРАБОТКЕ";

    private static final String CANCELED = "ОТМЕНЕН";

    private static final String CREATED = "ЗАКАЗ СОЗДАН!";

    @Value("${spring.kafka.topic.name}")
    private String OTNTopicName;

    @Value("${spring.kafka.topic.second_name}")
    private String OTWTopicName;

    @Value("${spring.kafka.topic.third_name}")
    private String OTDTopicName;

    private final OrderRepository orderRepository;

    private final ApplicationEventPublisher eventPublisher;

    private final OrderMapper orderMapper;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final OkHttpClient okHttpClient;


    private final Gson gson;

    private final WarehouseHttpClient warehouseHttpClient;


    @Override
    public ListOrderDto getAllOrders(Pageable pageable) {

        if (pageable == null || pageable.isUnpaged() || pageable.getPageSize() == 0) {
            pageable = PageRequest.of(0, 10);
        }

        Page<OrderEntity> orders = orderRepository.findAll(pageable);

        if (orders.getContent().isEmpty()) {
            throw new IllegalArgumentException("No orders found");
        }

        return new ListOrderDto(orders.getContent()
                        .stream()
                        .map(orderMapper::toDto)
                        .toList(), (int) orders.getTotalElements()
        );
    }




    @Override
    public List<OrderEntity> getOrderList() {
        return orderRepository.findAll();
    }

    @Override
    public Double getTotalRating(List<OrderEntity> orders) {
        if (orders == null || orders.isEmpty()) {
            return 0.0;
        }
        return orders.parallelStream()
                .filter(o -> o.getRating() != null)
                .mapToDouble(OrderEntity::getRating)
                .average()
                .orElse(0.0);
    }




    @Override
//    @Cacheable(value = "orderById",key = "#id")
    public OrderDto findOrderOnWarehouse(String id, String warehouseName) throws ExecutionException,
                                                                               InterruptedException,
                                                                                 TimeoutException {
        Objects.requireNonNull(id, "id must not be null");
        CompletableFuture<OrderDto> orderTask = CompletableFuture.supplyAsync(
                ()->findOrderById(id)
        );
        CompletableFuture<WarehouseResponse> warehouseTask = CompletableFuture.supplyAsync(
                ()-> {
                    try {
                        return warehouseHttpClient.getWarehouseId(warehouseName);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
        return orderTask.thenCombine(
                warehouseTask,(o,warehouseResponse)->{
                 o.setWarehouseId(warehouseResponse.getId());
                 o.setIsExists(warehouseResponse.getIsExists());
                 o.setCountOfProducts(warehouseResponse.getCountOfProducts());
                 o.setDeveloperName(warehouseResponse.getDeveloperName());
                 return o;
                }).exceptionally(
                        ex->{
                            throw new RuntimeException(ex.getMessage(),ex.getCause());
                        }).get(5, TimeUnit.SECONDS);


    }


    @Override
//    @Cacheable(value = "orderById",key = "#id")
    public OrderDto findOrderById(String id) {
        return orderMapper.toDto(orderRepository.findById(id)
                .orElseThrow(()-> new OrderNotFound("Order not found")));
    }


    @Transactional
    public void sendMessage(final OrderDto orderDto) {
        JsonObject jsonObject = getJsonObject(orderDto);
        String result = gson.toJson(jsonObject);
        log.info("Kafka message: {}", result);
        CompletableFuture.supplyAsync(
                () -> kafkaTemplate.send(OTNTopicName, result))
                .thenCompose(message -> message.whenComplete((r, e) -> {
                    if (e == null) {
                        log.info("Sent message to warehouse successful: {}", r);
                        log.info("Metadata of message: {}", r.getRecordMetadata().topic());
                    } else {
                        log.error("Failed to send to Kafka. Reason: {}", e.getMessage(), e);
                    }
                }))
//                .thenCompose(r -> kafkaTemplate.send(OTWTopicName, result))
                .whenComplete((r, e) -> {
                    if (e == null) {
                        log.info("Sent second message successfully.");
                    } else {
                        log.error("Failed to send second message to Kafka. Reason: {}", e.getMessage());
                    }
                })
                .exceptionally(e -> {
                    log.error("Unhandled error: {}", e.getMessage());
                    return null;
                });
    }


    private static JsonObject getJsonObject(final OrderDto orderDto) {
        KafkaEvent kafkaEvent = new KafkaEvent();
        kafkaEvent.setMessage(orderDto.getMessage());
        kafkaEvent.setName(orderDto.getName());
        kafkaEvent.setStatus(orderDto.getStatus());

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", orderDto.getId());
        jsonObject.addProperty("message", kafkaEvent.getMessage());
        jsonObject.addProperty("name", kafkaEvent.getName());
        jsonObject.addProperty("status", kafkaEvent.getStatus());
        return jsonObject;
    }

    @Override
    @CachePut(value = "orderAfterCreate", key = "#result.id")
    @CacheEvict(value = {"orders", "orderById"}, allEntries = true)
    @Transactional
    public OrderDto create(final OrderDto orderDto) {
        OrderEntity order;
        if (orderDto.getId() != null && orderRepository.existsById(orderDto.getId())) {
            order = orderRepository.findById(orderDto.getId())
                    .orElseThrow(() -> new OrderNotFound("Order not found"));
        } else {
            order = new OrderEntity();
            order.setId(UUID.randomUUID().toString());
            order.setName(orderDto.getName());
            order.setMessage(orderDto.getMessage());
            order.setStatus(CREATED);
        }
        log.info("Saving order: {}", order);

        orderRepository.save(order);

        eventPublisher.publishEvent(
                new OrderSavedEvent(
                        order.getId(),
                        order.getStatus(),
                        order.getIsExists()
                ));

        var dto = orderMapper.toDto(order);
        log.info("Saved order: {}", dto);
//        try {
//            sendMessage(dto);
//        } catch (Exception e) {
//            log.error("Failed to send message to Kafka: {}", dto.getId());
//        }
        return dto;
    }


    @Override
    public void delete(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Id cannot be null or blank");
        }
        if(!orderRepository.existsById(id)){
            throw new OrderNotFound("Order not found");
        }
        orderRepository.deleteById(id);
    }

    @Override
    public void deleteAllOrders(List<OrderEntity> orders) {
        if (orders == null || orders.isEmpty()) {
            return;
        }
        List<OrderEntity> orderToDelete = orders.stream()
                        .filter(order->order != null && order.getStatus().equals(DELIVERED))
                        .collect(Collectors.toCollection(ArrayList::new));
        if (orderToDelete.isEmpty()) {
            log.info("Not delivered orders found for deletion. Skipping");
        }
        try {
            log.info("Successfully deleted orders: {}",
                    orderToDelete.stream().map(OrderEntity::getId).toList());
            orderRepository.deleteAll(orderToDelete);
        }catch (Exception e){
            throw new RuntimeException("Order not found");
        }
    }

    @Override
    @CacheEvict(value = {"orders", "orderById"}, key = "#id",beforeInvocation = false)
    @Transactional
    public void updateOrderStatus(final String id,
                                  final OrderDto order) {

        if (order.getStatus() == null || order.getStatus().isBlank()) {
            throw new IllegalArgumentException("Status cannot be null or blank");
        }
        orderRepository.findById(id).ifPresentOrElse(o->{
                    o.setStatus(order.getStatus());
                    orderRepository.save(o);
                    eventPublisher.publishEvent(
                            new OrderSavedEvent(
                                    o.getId(),
                                    o.getStatus(),
                                    o.getIsExists()
                            )
                    );
                    log.info("UPDATED STATUS: {}", order.getStatus());
                }, ()-> {
                    throw new OrderNotFound("Order not found");
                });
    }



        }



