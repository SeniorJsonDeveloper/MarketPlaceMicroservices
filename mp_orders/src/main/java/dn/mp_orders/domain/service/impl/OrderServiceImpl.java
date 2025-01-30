package dn.mp_orders.domain.service.impl;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dn.mp_orders.api.client.WarehouseClient;
import dn.mp_orders.api.dto.KafkaRecord;
import dn.mp_orders.api.dto.ListOrderDto;
import dn.mp_orders.api.dto.OrderDto;
import dn.mp_orders.api.client.WarehouseResponse;
import dn.mp_orders.api.mapper.OrderMapper;
import dn.mp_orders.domain.entity.CommentEntity;
import dn.mp_orders.domain.entity.OrderEntity;
import dn.mp_orders.domain.exception.OrderNotFound;
import dn.mp_orders.domain.repository.CommentRepository;
import dn.mp_orders.domain.repository.OrderRepository;
import dn.mp_orders.domain.service.CommentService;
import dn.mp_orders.domain.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@CacheConfig(cacheManager = "redisCacheManager")
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

    private final OrderRepository orderRepository;

    private final CommentService commentService;

    private final CommentRepository commentRepository;

    private final OrderMapper orderMapper;

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final Gson gson;

    private final ApplicationContext applicationContext;

    private final WarehouseClient warehouseClient;

    @Override
    @Cacheable(cacheNames = "orderListWithPagination")
    public Page<OrderEntity> getAllOrders(Pageable pageable) {
        try {
            if (pageable == null) {
                pageable = PageRequest.of(0, 10);
            }
            return orderRepository.findAll(pageable);

        } catch (DataAccessException e) {
            log.error("Не удалось получить список заказов", e);
            return Page.empty();
        }
    }

    @Override
    @Cacheable(cacheNames = "orderList")
    public ListOrderDto getAllOrders() {
        return mapToListOrderDto(orderRepository.findAll());
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
    public OrderDto findOrderOnWarehouse(String id, String warehouseName) throws ExecutionException, InterruptedException {
        Objects.requireNonNull(id, "id must not be null");
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        var orderId = findOrderById(id);
        try {
            CompletableFuture<WarehouseResponse> warehouseTask = CompletableFuture.supplyAsync(
                            () -> warehouseClient.getWarehouseId(warehouseName), executorService)
                    .thenApply(warehouseId -> {
                        warehouseId.setIsExists(true);
                        return warehouseId;
                    }).handle((r, e) -> {
                        if (e != null) {
                            log.info("Exception from wareHouseTask is: {}", e.getMessage());
                        }
                        return r;
                    });

            CompletableFuture<OrderDto> orderTask = CompletableFuture.supplyAsync(
                    () -> orderId, executorService).thenCombineAsync(
                    warehouseTask, (order, wareHouseResponse) -> {
                        order.setWarehouseId(wareHouseResponse.getId());
                        order.setIsExists(wareHouseResponse.getIsExists());
                        return order;
                    }, executorService
            ).handle((order, exception) -> {
                if (exception != null) {
                    log.info("Exception from orderTask is: {}", exception.getMessage());
                    throw new RuntimeException(exception.getMessage(), exception.getCause());
                }
                return order;
            });
            return orderTask.get();
        }finally {
            executorService.shutdown();
        }

    }


    @Override
    public OrderDto findOrderById(String id) {
        return mapToDto(orderRepository.findById(id)
                .orElseThrow(()-> new OrderNotFound("Order not found")));
    }

    @SneakyThrows
    public void sendMessage(final OrderDto orderDto) {
        JsonObject jsonObject = getJsonObject(orderDto);
        String result = gson.toJson(jsonObject);
        log.info("Kafka message: {}", result);

        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(OTNTopicName, result);
        future.whenComplete((r, e) -> {
            if (e == null) {
                log.info("Kafka sent successfully. Offset: {}, Message: {}", r.getRecordMetadata().offset(), result);
            } else {
                log.error("Failed to send to Kafka. Reason: {}", e.getMessage(), e);
            }
        });
    }

    @Async
    public void sendAsyncMessage(final OrderDto orderDto) {
        CompletableFuture.runAsync(() -> {
            try {
                sendMessage(orderDto);
            } catch (Exception e) {
                log.error("Failed send message to Kafka: {}", orderDto.getId());
            }
        });
    }

    private static JsonObject getJsonObject(OrderDto orderDto) {
        KafkaRecord kafkaRecord = new KafkaRecord();
        kafkaRecord.setMessage(orderDto.getMessage());
        kafkaRecord.setName(orderDto.getName());
        kafkaRecord.setStatus(orderDto.getStatus());

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", orderDto.getId());
        jsonObject.addProperty("message", kafkaRecord.getMessage());
        jsonObject.addProperty("name", kafkaRecord.getName());
        jsonObject.addProperty("status", kafkaRecord.getStatus());
        return jsonObject;
    }

    @Override
    @CachePut(value = "orderAfterCreate", key = "#result.id")
    @CacheEvict(value = {"orders", "orderById"}, allEntries = true)
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

        var dto = mapToDto(order);
        log.info("Saved order: {}", dto);
//        try {
//            sendAsyncMessage(dto);
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
    public void updateOrderStatus(final String id,
                                  final OrderDto order) {

        if (order.getStatus() == null || order.getStatus().isBlank()) {
            throw new IllegalArgumentException("Status cannot be null or blank");
        }
        orderRepository.findById(id).ifPresentOrElse(o->{
                    o.setStatus(order.getStatus());
                    orderRepository.save(o);
                    sendAsyncMessage(mapToDto(o));
                    log.info("UPDATED STATUS: {}", order.getStatus());
                }, ()-> {
                    throw new OrderNotFound("Order not found");
                });
    }

    @Scheduled(cron = "0 0 * * * *")
    public void getAvgRatingByComments(){
        try {
            List<CommentEntity> comments = (List<CommentEntity>) commentRepository.findAll();
            if (comments.isEmpty()) {
                log.warn("No comments found. Skipping average rating calculation.");
                return;
            }
            var rating = commentService.getRatingByComments(comments);
            log.info("Average Rating: {}",rating);
        } catch (Exception e) {
            log.error("Failed to calculate average rating: {}", e.getMessage(), e);
        }
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void cleanAllOrders(){
        try {
            OrderService proxy = applicationContext.getBean(OrderService.class);
            List<OrderEntity> orders = proxy.getAllOrders()
                    .getOrderDtoList().stream()
                    .map(orderMapper::toEntity)
                    .toList();
            if (!orders.isEmpty()) {
                log.warn("No orders found. Skipping cache cleaning.");
            }
            orderRepository.deleteAll(orders);
            log.info("Cache cleaned: {}", orders.stream().map(OrderEntity::getId).toList());
        }catch (Exception e){
            log.error("Failed to clean cache: {}", e.getMessage(), e);
        }
    }


    public OrderDto mapToDto(OrderEntity order) {
        if (order == null) {
            throw new OrderNotFound("Order not found");
        }
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setName(order.getName());
        orderDto.setMessage(order.getMessage());
        orderDto.setStatus(order.getStatus());
        orderDto.setPrice(order.getPrice());
        orderDto.setWarehouseId(order.getWarehouseId());
        return orderDto;
    }

    private ListOrderDto mapToListOrderDto(List<OrderEntity> orders) {

        if (orders == null || orders.isEmpty()) {
            return new ListOrderDto();
        }
        ListOrderDto listOrderDto = new ListOrderDto();
        listOrderDto.setOrderDtoList(orders.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList()));
        return listOrderDto;
    }


}

