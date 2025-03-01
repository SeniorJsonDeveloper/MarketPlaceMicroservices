
package dn.mp_orders.domain.service.impl;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dn.mp_orders.api.client.WarehouseHttpClient;
import dn.mp_orders.api.client.WarehouseResponse;
import dn.mp_orders.api.dto.ListOrderDto;
import dn.mp_orders.api.dto.OrderDto;
import dn.mp_orders.api.exception.AbstractNotFoundExceptionException;
import dn.mp_orders.api.exception.OrderNotFound;
import dn.mp_orders.api.mapper.OrderMapper;
import dn.mp_orders.domain.entity.OrderEntity;
import dn.mp_orders.domain.event.KafkaEvent;
import dn.mp_orders.domain.event.OrderSavedEvent;
import dn.mp_orders.domain.repository.OrderRepository;
import dn.mp_orders.domain.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

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

    private final RestClient restClient;

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
        return orders.stream()
                .filter(o -> o.getRating() != null)
                .mapToDouble(OrderEntity::getRating)
                .average()
                .orElse(0.0);
    }

    @Override
    public BigDecimal getPriceOfProduct(Long productId) {
        var request = restClient.get()
                .uri("http://localhost:8081/api/v1/product/{id}", productId)
                .retrieve()
                .body(BigDecimal.class);
        log.info("Price of product: {}", request);
        return Optional.ofNullable(request)
                .filter(p -> p.doubleValue() != 0)
                .orElseThrow(() -> new AbstractNotFoundExceptionException("Product not found"));

    }

    @Override
    public Long getCountOfProductsById(Long productId) {
        try {
            var response = restClient.get()
                    .uri("http://localhost:8081/api/v1/product/{id}/count", productId)
                    .retrieve()
                    .body(Long.class);
            return Objects.requireNonNull(response);
        } catch (Exception e) {
            log.error("Error: {}", e.getMessage());
            return null;
        }
    }



    @Override
    @Cacheable(value = "orderById",key = "#id")
    public OrderDto findOrderOnWarehouse(Long id, String warehouseName) throws ExecutionException,
            InterruptedException,
            TimeoutException {
        Objects.requireNonNull(id, "id must not be null");
        CompletableFuture<OrderDto> orderTask = CompletableFuture.supplyAsync(
                () -> findOrderById(id)
        );
        CompletableFuture<WarehouseResponse> warehouseTask = CompletableFuture.supplyAsync(
                () -> {
                    try {
                        return warehouseHttpClient.getWarehouseId(warehouseName);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
        return orderTask.thenCombine(
                warehouseTask, (o, warehouseResponse) -> {
                    o.setWarehouseId(warehouseResponse.getId());
                    o.setIsExists(warehouseResponse.getIsExists());
                    o.setCountOfProducts(warehouseResponse.getCountOfProducts());
                    o.setDeveloperName(warehouseResponse.getDeveloperName());
                    return o;
                }).exceptionally(
                ex -> {
                    throw new RuntimeException(ex.getMessage(), ex.getCause());
                }).get(5, TimeUnit.SECONDS);


    }


    @Override
    @Cacheable(value = "orderById",key = "#id")
    public OrderDto findOrderById(Long id) {
        return orderMapper.toDto(orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFound("Order not found")));
    }


    public void sendMessage(final OrderDto orderDto) {
        JsonObject jsonObject = getJsonObject(orderDto);
        String result = gson.toJson(jsonObject);
        if (result.isBlank()) {
            throw new IllegalArgumentException("Json result is blank");
        }
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
    public OrderDto create(OrderDto orderDto) {
        OrderEntity order = new OrderEntity();
        if (orderDto.getId() != null && orderRepository.existsById(orderDto.getId())) {
            order = orderRepository.findById(orderDto.getId())
                    .orElseThrow(() -> new OrderNotFound("Order not found"));
        }
        var price = getPriceOfProduct(orderDto.getProductId());
        var countOfProducts = getCountOfProductsById(orderDto.getProductId());
        var productId = orderDto.getProductId();
        order.setId(order.getId());
        order.setName(orderDto.getName());
        order.setMessage(orderDto.getMessage());
        order.setStatus(CREATED);
        order.setPrice(price);
        order.setCountOfProducts(countOfProducts);
        order.setProductId(Collections.singleton(productId));
        log.info("Saving order: {}", order);
        orderRepository.save(order);

        eventPublisher.publishEvent(
                    new OrderSavedEvent(
                            order.getId(),
                            order.getStatus(),
                            order.getPrice(),
                            order.getCountOfProducts(),
                            order.getIsExists(),
                            order.getProductId()
                    ));
        return orderMapper.toDto(order);
        }



    @Override
    public void delete(Long id) {
        if (id == null) {
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
        List<OrderEntity> ordersForDelete = orders.stream()
                        .filter(order->order != null && order.getStatus().equals(DELIVERED))
                        .collect(Collectors.toCollection(ArrayList::new));
        if (ordersForDelete.isEmpty()) {
            log.info("Not delivered orders found for deletion. Skipping");
        }
        try {
            log.info("Successfully deleted orders: {}",
                    ordersForDelete.stream()
                            .map(OrderEntity::getId)
                            .toList());
            orderRepository.deleteAll(ordersForDelete);
        }catch (Exception e){
            throw new RuntimeException("Order not found");
        }
    }

    @Override
    @CacheEvict(value = {"orders", "orderById"}, key = "#id",beforeInvocation = false)
    public void updateOrderStatus(final Long id,
                                  final OrderDto order) {
//
//        if (order.getStatus() == null || order.getStatus().isBlank()) {
//            throw new IllegalArgumentException("Status cannot be null or blank");
//        }
//        orderRepository.findById(id).ifPresentOrElse(o->{
//                    o.setStatus(order.getStatus());
//                    orderRepository.save(o);
//                    eventPublisher.publishEvent(
//                            new OrderSavedEvent(
//                                    o.getId(),
//                                    o.getStatus(),
//                                    o.getIsExists()
//                            )
//                    );
//                    log.info("UPDATED STATUS: {}", order.getStatus());
//                }, ()-> {
//                    throw new OrderNotFound("Order not found");
//                });
    }



        }



