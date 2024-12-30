package dn.mp_notifications.domain.configuration;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dn.mp_notifications.api.OrderDto;
import dn.mp_notifications.domain.service.KafkaHandler;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.json.simple.parser.JSONParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.JsonPath;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.json.simple.*;

import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class KafkaListenerConfig {

    private final Map<String, KafkaHandler> orders;

    private final Gson gson;

    private final ObjectMapper objectMapper;


    @SneakyThrows
    @KafkaListener(topics = "OrderToNotifications", groupId = "1")
    public void listen(final String payload,
                       final Acknowledgment acknowledgment) {
        log.info("Received payload: {}", payload);
        try {
            String message = JsonParser.parseString(payload)
                    .getAsJsonObject()
                    .get("message")
                    .getAsString();
            String name = JsonParser.parseString(payload)
                    .getAsJsonObject()
                    .get("name")
                    .getAsString();

            String status = JsonParser.parseString(payload)
                    .getAsJsonObject()
                    .get("status")
                    .getAsString();
            OrderDto dto = new OrderDto();
            dto.setMessage(message);
            dto.setName(name);
            dto.setStatus(status);
            log.info("Received message: {}", dto.getMessage());
            log.info("Received name: {}", dto.getName());
            log.info("Received status: {}", dto.getStatus());
        }catch (Exception e){
            log.error("Error parsing json payload", e);
        }


    }

}

