package dn.mp_notifications.domain.service;

import dn.mp_notifications.api.dto.MessageDto;
import dn.mp_notifications.domain.entity.Notification;
import dn.mp_notifications.domain.entity.NotificationDto;

import java.util.concurrent.CompletableFuture;


public interface SenderService {

    String generateCode();

    NotificationDto sendNotification(MessageDto messageDto, String orderId);


    String sendSmsCode(String phoneNumber);

    Iterable<Notification> findAllNotifications();

    NotificationDto findNotificationById(String id);




}
