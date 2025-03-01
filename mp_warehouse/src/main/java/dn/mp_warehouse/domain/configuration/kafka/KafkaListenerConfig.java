package dn.mp_warehouse.domain.configuration.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dn.mp_warehouse.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaListenerConfig {


    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    private final ProductRepository productRepository;

    private final ObjectMapper objectMapper;

    private final Gson gson = new Gson()
            .newBuilder()
            .create();
    @KafkaListener(topics = "${spring.kafka.topic.name}",
            groupId = "${spring.kafka.consumer.group-id}")
    public void listen(final String payload){
        try {
            final String orderCreated = "ЗАКАЗ СОЗДАН!";
            JsonElement jsonElement = JsonParser.parseString(payload);
            if (jsonElement.isJsonPrimitive() && jsonElement.getAsJsonPrimitive().isString()) {
                jsonElement = JsonParser.parseString(jsonElement.getAsString());
            }

            JsonObject jsonObject = jsonElement.getAsJsonObject();
            var status = jsonObject.get("status").getAsString();
            log.info("Status of order is {}", status);
            Long productId = jsonObject.get("productId").getAsLong();
            log.info("Product id is {}", productId);

            if (status.equals(orderCreated)) {
                var product = productRepository.findById(productId);
                product.ifPresent(productRepository::delete);
                log.info("Product with id {} deleted", productId);
            }
        } catch (Exception e) {
            log.error("Error while listening to kafka topic", e);
        }
    }



    @Bean
    public ConsumerFactory<String, String> consumerFactory(ObjectMapper objectMapper) {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String,String> listenerFactory(ConsumerFactory<String, String> stringConsumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(stringConsumerFactory);
        factory.setBatchListener(false);
        return factory;
    }


}
