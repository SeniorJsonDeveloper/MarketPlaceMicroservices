package dn.mp_notifications.domain.configuration;

import com.google.gson.JsonParser;
import dn.mp_notifications.domain.event.MessageEvent;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

import java.math.BigDecimal;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class KafkaListenerConfig {


    private final ApplicationEventPublisher eventPublisher;



    @KafkaListener(topics = "OrderToNotifications", groupId = "1")
    public void listen(final String payload) {
        log.info("Received payload: {}", payload);
        try {
            String message = JsonParser.parseString(payload)
                    .getAsJsonObject()
                    .get("message")
                    .getAsString();
            String status = JsonParser.parseString(payload)
                    .getAsJsonObject()
                    .get("status")
                    .getAsString();
            Long id = JsonParser.parseString(payload)
                    .getAsJsonObject()
                    .get("id")
                    .getAsLong();
            eventPublisher.publishEvent(
                    new MessageEvent(
                    id,message,status, BigDecimal.ZERO)
            );
        }catch (Exception e){
            log.error("Error parsing json payload", e);
        }


    }

}

