package dn.mp_notifications.domain.service.impl;

import dn.mp_notifications.domain.entity.Notification;
import dn.mp_notifications.domain.entity.NotificationDto;
import dn.mp_notifications.domain.repository.NotificationRepository;
import dn.mp_notifications.domain.service.SenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements SenderService {

    private final NotificationRepository notificationRepository;



    @Override
    public Notification create(Notification notificationDto) {
        Notification notification = new Notification();
        notification.setId(UUID.randomUUID().toString());
        notification.setMessage(notificationDto.getMessage());
        notification.setTitle(notificationDto.getTitle());
        return notificationRepository.save(notification);
    }
}
