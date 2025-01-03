package dn.mp_notifications.domain.service;

import dn.mp_notifications.domain.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationScheduler {

    private final NotificationRepository notificationRepository;

//    @Scheduled(fixedRate = 5000)
    public void clean(){
        notificationRepository.deleteAll();
    }
}
