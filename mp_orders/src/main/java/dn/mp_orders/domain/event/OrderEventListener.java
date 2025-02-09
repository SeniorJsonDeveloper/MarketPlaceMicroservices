package dn.mp_orders.domain.event;

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

    private final KafkaTemplate<String,Object> kafkaTemplate;

    @Value("${spring.kafka.topic.name}")
    private String OTNTopicName;

    @Value("${spring.kafka.topic.second_name}")
    private String OTWTopicName;

    private final Gson gson;

    @EventListener(OrderSavedEvent.class)
    public void listenOrderSavedEvent(OrderSavedEvent event){
        var result = gson.toJson(event);
        if (result.isBlank()){
            throw new BadRequestException("Cant convert event to json");
        }
        CompletableFuture.supplyAsync(() ->
            kafkaTemplate.send(OTWTopicName,result))
                .thenCompose(v -> kafkaTemplate.send(OTNTopicName,result))
                .whenComplete((v,e) -> {
              if (e == null) {
                  log.info("Messages was sent to kafka");
              } else {
                  log.error("Failed to send second message to Kafka. Reason: {}", e.getMessage());
              }
          }).exceptionally(
                  ex -> {
                      log.error("Unhandled error: {}", ex.getMessage());
                      return null;
                  }
                );
    }
}
