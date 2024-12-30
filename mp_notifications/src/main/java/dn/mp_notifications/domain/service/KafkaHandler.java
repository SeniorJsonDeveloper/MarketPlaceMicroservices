package dn.mp_notifications.domain.service;

import com.google.gson.JsonObject;
import org.springframework.kafka.support.Acknowledgment;

public interface KafkaHandler {

    void handle(JsonObject payload, Acknowledgment acknowledgment);
}
