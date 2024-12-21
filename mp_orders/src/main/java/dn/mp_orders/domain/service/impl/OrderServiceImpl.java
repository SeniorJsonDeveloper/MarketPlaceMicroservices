package dn.mp_orders.domain.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dn.mp_orders.api.dto.KafkaDto;
import dn.mp_orders.api.dto.OrderDto;
import dn.mp_orders.api.dto.Status;
import dn.mp_orders.domain.OrderEntity;
import dn.mp_orders.domain.configuration.LocalDateDeserializer;
import dn.mp_orders.domain.exception.OrderNotFound;
import dn.mp_orders.domain.repository.OrderRepository;
import dn.mp_orders.domain.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final RedisTemplate<String, Object> redisTemplate;

    private final ObjectMapper objectMapper;

    private final Gson gson;


    @SneakyThrows
    public void sendMessage(OrderDto orderDto) {
        Map<String, Object> message = new HashMap<>();
        var id = orderDto.getId();
        KafkaDto kafkaDto = new KafkaDto();
        kafkaDto.setMessage("Ваш заказ создан");
        kafkaDto.setStatus(Status.СОЗДАН.toString());
        kafkaDto.setCreatedAt(true);
        message.put(id, kafkaDto);
        String kafkaMessage = gson.toJson(message);
        kafkaTemplate.send("OrdersToNotifications",id, kafkaMessage);

    }





    @Override
    @Cacheable(cacheNames = "orderAfterCreate",key = "#orderDto.id",condition = "#orderDto.id!=null")
    @SneakyThrows
    public OrderDto save(OrderDto orderDto) {
        OrderEntity order = new OrderEntity();
        order.setId(UUID.randomUUID().toString());
        order.setName(orderDto.getName());
        order.setStatus(true);
        orderRepository.save(order);
        OrderDto dto = new OrderDto();
        dto.setId(order.getId());
        dto.setName(order.getName());
        dto.setStatus(order.getStatus());
        sendMessage(dto);
        return dto;
    }

    @Override
    @Cacheable(cacheNames = "orderById",key = "#id")
    public OrderEntity findById(String id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFound("Order not found"));
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
    public OrderEntity update(String id, OrderDto order) {
        OrderEntity requireOrder = findById(id);
        if (!requireOrder.getId().equals(id)) {
            throw new OrderNotFound("Order not found");
        }
        requireOrder.setId(UUID.randomUUID().toString());
        requireOrder.setName(order.getName());
        requireOrder.setPrice(order.getPrice());
        requireOrder.setCreatedAt(LocalDateTime.now());
        return orderRepository.save(requireOrder);

    }
}
