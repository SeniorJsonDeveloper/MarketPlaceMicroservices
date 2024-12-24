package dn.mp_notifications.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import dn.mp_notifications.api.OrderDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.client.RestClient;

import java.io.IOException;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class KafkaConfig {

    private final Gson gson;


    @SneakyThrows
    @KafkaListener(topics = "OrderToNotifications",groupId = "1")
    public void listen(@Payload String message) {
        OrderDto orderDto = gson.fromJson(message, OrderDto.class);
        log.info("orderMessage: {}", orderDto.getMessage());

    }
}
