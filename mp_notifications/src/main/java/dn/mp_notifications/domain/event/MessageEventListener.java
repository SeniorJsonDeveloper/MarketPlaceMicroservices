package dn.mp_notifications.domain.event;

import dn.mp_notifications.domain.service.impl.NotificationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageEventListener {

    private final NotificationServiceImpl notificationService;


    @EventListener(MessageEvent.class)
    public void handleMessageEvent(MessageEvent event) {
        notificationService.sendNotification(event,event.getOrderId());

    }
}
