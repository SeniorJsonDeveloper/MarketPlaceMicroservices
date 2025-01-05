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

    private final ObjectMapper objectMapper;

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final OrderMapper orderMapper;

    private final Gson gson;


    @SneakyThrows
    @Transactional
    public void sendMessage(OrderDto orderDto) {
        Map<String, Object> message = new HashMap<>();
        var id = orderDto.getId();
        KafkaDto kafkaDto = new KafkaDto();
        kafkaDto.setMessage(orderDto.getMessage());
        kafkaDto.setName(orderDto.getName());
        kafkaDto.setStatus(orderDto.getStatus());
        message.put(id, kafkaDto);

        String kafkaDtoName = objectMapper.convertValue(kafkaDto, KafkaDto.class).getName();
        String kafkaDtoMessage = objectMapper.convertValue(kafkaDto, KafkaDto.class).getMessage();
        String kafkaDtoCreatedAt = objectMapper.convertValue(kafkaDto, KafkaDto.class).getStatus();
        kafkaDto.setMessage(kafkaDtoMessage);
        kafkaDto.setStatus(kafkaDtoCreatedAt);
        kafkaDto.setName(kafkaDtoName);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("message", kafkaDto.getMessage());
        jsonObject.addProperty("name", kafkaDto.getName());
        jsonObject.addProperty("status",kafkaDto.getStatus());
        jsonObject.addProperty("id", id);
        var result = gson.toJson(jsonObject);

        log.info("result: {}", result);

        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicName,result);
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
        order.setStatus(orderDto.getStatus());
        order.setMessage(orderDto.getMessage());
        orderRepository.save(order);
        OrderDto dto = orderMapper.toDto(order);
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
    @Transactional
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

    @Override
    @SneakyThrows
    @Cacheable("orders")
    public Iterable<OrderDto> getAllOrders() {
        return orderMapper.toIterable(orderRepository.findAll());


    }




}

