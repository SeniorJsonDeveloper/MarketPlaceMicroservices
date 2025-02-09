package dn.mp_notifications.domain.service;
import dn.mp_notifications.domain.entity.NotificationEntity;
import dn.mp_notifications.domain.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;


@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationScheduler {

    private final NotificationRepository notificationRepository;

    private final SenderService senderService;

    @Scheduled(fixedRate = 100)
    public void deleteAlreadyMadeNotifications() {
        List<NotificationEntity> notificationEntities = (List<NotificationEntity>) notificationRepository.findAll();
        log.info("Deleted notifications: {}", notificationEntities);
        senderService.deleteNotifications(notificationEntities);
    }


}
