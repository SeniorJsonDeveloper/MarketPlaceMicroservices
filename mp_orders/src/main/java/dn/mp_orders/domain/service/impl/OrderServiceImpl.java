package dn.mp_orders.domain.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.DeserializerFactory;
import com.google.gson.Gson;
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
import org.springframework.boot.json.JsonParser;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.serializer.Deserializer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    @Value("${spring.kafka.topic.name}")
    private String topicName;

    private final OrderRepository orderRepository;

    private final KafkaTemplate<String, Object> kafkaTemplate;


    private final OrderMapper orderMapper;

    private final Gson gson;


    @SneakyThrows
    @Transactional
    public void sendMessage(OrderDto orderDto) {
        Map<String, Object> message = new HashMap<>();
        var id = orderDto.getId();
        KafkaDto kafkaDto = new KafkaDto();
        kafkaDto.setMessage(orderDto.getMessage());
        kafkaDto.setStatus(Status.СОЗДАН.toString());
        kafkaDto.setCreatedAt(true);
        message.put(id, kafkaDto);
        String kafkaMessage = gson.toJson(message);
        CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topicName,kafkaMessage);
        future.whenComplete((r, e) -> {
            if (e == null) {
                log.info("KAFKA DATA: {}, {}", r.getRecordMetadata().offset(), message);
            }
            else {
                log.info("CANT SEND MESSAGE: {}, {}", e.getMessage(), message);
            }
        });


    }




    @Override
    @Cacheable(cacheNames = "orderAfterCreate", key = "#orderDto.id", condition = "#orderDto.id!=null")
    @Transactional
    public OrderDto save(OrderDto orderDto) {
        OrderEntity order = new OrderEntity();
        order.setId(UUID.randomUUID().toString());
        order.setName(orderDto.getName());
        order.setStatus(true);
        order.setMessage(orderDto.getMessage());
        orderRepository.save(order);
        OrderDto dto = new OrderDto();
        dto.setId(order.getId());
        dto.setName(order.getName());
        dto.setStatus(order.getStatus());
        dto.setCreatedAt(LocalDateTime.now());
        dto.setMessage(orderDto.getMessage());
        sendMessage(dto);
        return dto;
    }

    @Override
    @Cacheable(cacheNames = "orderById", key = "#id")
    public OrderDto findById(String id) {
        return orderMapper.toDto(orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFound("Order not found")));
    }

    @Override
    public void delete(String id) {
        orderRepository.deleteById(id);
    }

    @Override
    public void deleteAllOrders() {
        orderRepository.deleteAll();
    }

    @Override
    @CachePut(cacheNames = "orderAfterUpdate")
    public OrderDto update(String id, OrderDto order) {
        OrderEntity requireOrder = orderMapper.fromDto(findById(id));
        if (!requireOrder.getId().equals(id)) {
            throw new OrderNotFound("Order not found");
        }
        return orderMapper.toDto(orderRepository.save(requireOrder));

    }

    @Override
    @SneakyThrows
    @Cacheable("orders")
    public Iterable<OrderDto> getAllOrders() {
        return orderMapper.toIterable(orderRepository.findAll());


    }




}

