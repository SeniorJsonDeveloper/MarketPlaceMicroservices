package dn.mp_orders.domain.service.impl;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dn.mp_orders.api.dto.CommentDto;
import dn.mp_orders.api.dto.KafkaRecord;
import dn.mp_orders.api.dto.ListOrderDto;
import dn.mp_orders.api.dto.OrderDto;
import dn.mp_orders.api.mapper.CommentMapper;
import dn.mp_orders.api.mapper.OrderMapper;
import dn.mp_orders.domain.entity.CommentEntity;
import dn.mp_orders.domain.entity.OrderEntity;
import dn.mp_orders.domain.exception.OrderNotFound;
import dn.mp_orders.domain.repository.CommentRepository;
import dn.mp_orders.domain.repository.OrderRepository;
import dn.mp_orders.domain.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import org.springframework.amqp.core.Queue;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private static final String DELIVERED = "ДОСТАВЛЕН";

    private static final String ON_THE_WAY = "В ПУТИ";

    private static final String PROCESSING = "В ОБРАБОТКЕ";

    private static final String CANCELED = "ОТМЕНЕН";

    private static final String CREATED = "ЗАКАЗ СОЗДАН!";


    @Value("${spring.kafka.topic.name}")
    private String topicName;

    private final OrderRepository orderRepository;

    private final CommentRepository commentRepository;

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final Gson gson;

    private final OrderMapper orderMapper;

    @Override
    public List<OrderEntity> getAllOrders() {
        return orderRepository.findAll();


    }

    @Override
    public Double getTotalRating(List<OrderEntity> orders) {
        return orders.stream()
                .filter(o -> o.getRating() != null)
                .mapToDouble(OrderEntity::getRating)
                .average()
                .orElse(0.0);
    }

    @Override
    @Cacheable("ordersWithComments")
    public OrderDto getCommentsForOrder(String orderId, Set<CommentDto> commentEntitySet){
//        if (commentEntitySet == null || commentEntitySet.isEmpty()){
//            throw new CommentNotFoundException("Comments not found for order");
//        }
//        OrderService proxy = applicationContext.getBean(OrderService.class);
//        OrderDto orderDto = proxy.findById(orderId);
//        Set<CommentDto> comments = Optional
//                .ofNullable(orderDto.getComments())
//                .orElse(new HashSet<>());
//        comments.addAll(commentEntitySet);
//        orderDto.setComments(comments);

        return null; //TODO:

    }


    @Override
    @Cacheable(cacheNames = "orderById", key = "#id")
    public OrderEntity findById(String id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFound("Order not found"));
    }

    @SneakyThrows
    @Transactional
    public void sendMessage(OrderDto orderDto) {
        JsonObject jsonObject = getJsonObject(orderDto);
        String result = gson.toJson(jsonObject);
        log.info("Kafka message: {}", result);

        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicName, result);
        future.whenComplete((r, e) -> {
            if (e == null) {
                log.info("Kafka sent successfully. Offset: {}, Message: {}", r.getRecordMetadata().offset(), result);
            } else {
                log.error("Failed to send to Kafka. Reason: {}", e.getMessage(), e);
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
    @Transactional
    public OrderDto save(OrderDto orderDto) {
        OrderEntity order = new OrderEntity();
        order.setId(UUID.randomUUID().toString());
        order.setName(orderDto.getName());
        order.setStatus(CREATED);
        order.setMessage(orderDto.getMessage());
        order.setRating(0.0);
        orderRepository.save(order);
        OrderDto dto = orderMapper.toDto(order);
        sendMessage(dto);
        log.info("Saved order: {}", dto.toString());
        return dto;
    }


    @Override
    public void delete(String id) {
        orderRepository.deleteById(id);
    }

    @Override
    public void deleteAllOrders(List<OrderEntity> orders) {
        List<OrderEntity> orderToDelete = orders.stream()
                        .filter(order->order != null && order.getStatus().equals(DELIVERED))
                        .toList();
        orderRepository.deleteAll(orderToDelete);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"orders", "orderById"}, key = "#id")
    public void updateOrderStatus(String id, OrderDto order) {
        orderRepository.findById(id).ifPresentOrElse(o->{
                    o.setStatus(order.getStatus());
                    orderRepository.save(o);
                    sendMessage(orderMapper.toDto(o));
                    log.info("UPDATED STATUS: {}", order.getStatus());
                }, ()-> {
                    throw new OrderNotFound("Order not found");
                });


    }





}

