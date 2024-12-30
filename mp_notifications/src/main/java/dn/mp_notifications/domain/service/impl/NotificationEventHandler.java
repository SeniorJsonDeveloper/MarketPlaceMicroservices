package dn.mp_notifications.domain.service.impl;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dn.mp_notifications.domain.entity.Notification;
import dn.mp_notifications.domain.service.KafkaHandler;
import dn.mp_notifications.domain.service.SenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationEventHandler implements KafkaHandler {

    private final SenderService senderService;

    private final Gson gson;


    @Override
    public void handle(final JsonObject payload,
                       final Acknowledgment acknowledgment) {

        Notification notification = gson.fromJson(payload, Notification.class);
        senderService.create(notification);


    }
}
