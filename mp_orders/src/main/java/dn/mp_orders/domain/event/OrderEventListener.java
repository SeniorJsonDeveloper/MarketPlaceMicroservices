package dn.mp_orders.domain.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import dn.mp_orders.api.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;


@Component
@Slf4j
@RequiredArgsConstructor
public class OrderEventListener {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${spring.kafka.topic.name}")
    private String OTNTopicName;

    @Value("${spring.kafka.topic.second_name}")
    private String OTWTopicName;

    private final Gson gson;

    @EventListener(OrderSavedEvent.class)
    public void listenOrderSavedEvent(OrderSavedEvent event) throws JsonProcessingException {
        if (event == null) {
            throw new BadRequestException("Event is null");
        }
        log.info("Order saved event received: {}", event);
        var message = gson.toJson(event);
        if (message.isBlank()) {
            throw new BadRequestException("Cant convert event to json");
        }
        log.info("Kafka message: {}", message);
        CompletableFuture<Void> sendMessages = CompletableFuture.allOf(
                CompletableFuture.runAsync(() -> kafkaTemplate.send(
                        OTWTopicName, message
                )),
                CompletableFuture.runAsync(() -> kafkaTemplate.send(
                        OTNTopicName, message
                )));
        sendMessages.whenComplete((v, e) -> {
            if (e == null) {
                log.info("Messages was sent to kafka");
            } else {
                log.error("Failed to send second message to Kafka. Reason: {}", e.getMessage());
            }
        }).exceptionally(
                ex -> {
                    log.error("Unhandled error {}", ex.getMessage());
                    return null;
                });
    }
}
