package dn.mp_orders.domain.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.DeserializerFactory;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dn.mp_orders.api.dto.KafkaDto;
import dn.mp_orders.api.dto.OrderDto;
import dn.mp_orders.api.dto.Status;
import dn.mp_orders.api.mapper.OrderMapper;
import dn.mp_orders.domain.OrderEntity;
import dn.mp_orders.domain.exception.OrderNotFound;
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

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {


    private static final String DELIVERED = "ДОСТАВЛЕН";

    private static final String ON_THE_WAY = "В ПУТИ";

    private static final String PROCESSING = "В ОБРАБОТКЕ";

    @Value("${spring.kafka.topic.name}")
    private String topicName;


    private final OrderRepository orderRepository;

    private final ObjectMapper objectMapper;

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final OrderMapper orderMapper;

    private final Gson gson;

    @Override
    @SneakyThrows
    @Cacheable("orders")
    public List<OrderDto> getAllOrders() {
        return  orderMapper.toDto((List<OrderEntity>)orderRepository.findAll());

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
    @Cacheable(cacheNames = "orderById", key = "#id")
    public OrderDto findById(String id) {
        return orderMapper.toDto(orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFound("Order not found")));
    }


    @SneakyThrows
    @Transactional
    public void sendMessage(OrderDto orderDto) {
        KafkaDto kafkaDto = new KafkaDto();
        kafkaDto.setMessage(orderDto.getMessage());
        kafkaDto.setName(orderDto.getName());
        kafkaDto.setStatus(orderDto.getStatus());

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", orderDto.getId());
        jsonObject.addProperty("message", kafkaDto.getMessage());
        jsonObject.addProperty("name", kafkaDto.getName());
        jsonObject.addProperty("status", kafkaDto.getStatus());

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




    @Override
    @CacheEvict(value = "orders", allEntries = true)
    @Transactional
    public OrderDto save(OrderDto orderDto) {
        OrderEntity order = new OrderEntity();
        order.setId(UUID.randomUUID().toString());
        order.setName(orderDto.getName());
        order.setStatus(orderDto.getStatus());
        order.setMessage(orderDto.getMessage());
        orderRepository.save(order);
        OrderDto dto = orderMapper.toDto(order);
        sendMessage(dto);
        return dto;
    }


    @Override
    public void delete(String id) {
        orderRepository.deleteById(id);
    }

    @Override
    @CacheEvict(value = "orders", allEntries = true)
    public void deleteAllOrders(List<OrderEntity> orders) {
        List<OrderEntity> orderToDelete = orders
                        .stream()
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

