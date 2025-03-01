package dn.mp_notifications.domain.event;

import dn.mp_notifications.domain.entity.NotificationEntity;
import dn.mp_notifications.domain.service.impl.NotificationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class MessageEventListener {

    private final NotificationServiceImpl notificationService;

    private final KafkaTemplate<Long, NotificationEntity> kafkaTemplate;


    @EventListener(MessageEvent.class)
    public void handleMessageEvent(MessageEvent event) {
        notificationService.sendNotification(event,event.getOrderId());
    }


    @TransactionalEventListener
    @Transactional(rollbackFor = Exception.class)
    public void handleMessageEventTransactional(MessageEvent event) {
        notificationService.sendNotification(event,event.getOrderId());
        
    }
}
