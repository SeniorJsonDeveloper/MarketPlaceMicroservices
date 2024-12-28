package dn.mp_notifications.domain.configuration;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dn.mp_notifications.api.OrderDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.json.simple.parser.JSONParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
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

    private final Gson gson;

    private final ObjectMapper objectMapper;


    @SneakyThrows
    @KafkaListener(topics = "OrderToNotifications", groupId = "1")
    public void listen(@Payload String message,
                       @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        log.info("Received message: {}", message);
    }

}

