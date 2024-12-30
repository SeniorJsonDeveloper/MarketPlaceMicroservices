package dn.mp_notifications.domain.service;

import dn.mp_notifications.domain.entity.Notification;
import dn.mp_notifications.domain.entity.NotificationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


public interface SenderService {

    Notification create(final Notification notificationDto);
}
