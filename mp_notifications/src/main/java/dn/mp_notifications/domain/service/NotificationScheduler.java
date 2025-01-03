package dn.mp_notifications.domain.service;
import dn.mp_notifications.api.dto.MessageDto;
import dn.mp_notifications.domain.entity.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class NotificationScheduler {

    private final SenderService senderService;


    @Scheduled(fixedRate = 1000)
    public void clean(){
        List<Notification> notifications =(List<Notification>) senderService.findAllNotifications();

        senderService.deleteNotification(notifications);
    }
}
