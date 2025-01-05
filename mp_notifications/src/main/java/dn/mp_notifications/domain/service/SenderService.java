package dn.mp_notifications.domain.service;

import dn.mp_notifications.api.dto.MessageDto;
import dn.mp_notifications.api.dto.PageOutDto;
import dn.mp_notifications.domain.entity.Notification;
import dn.mp_notifications.api.dto.NotificationDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface SenderService {

    String generateCode();

    NotificationDto sendNotification(MessageDto messageDto, String orderId);


    String sendSmsCode(String phoneNumber);

    Page<Notification> findAllNotifications(NotificationDto notificationDto);

    NotificationDto findNotificationById(String id);

    void deleteNotification(List<Notification> notifications);

    void addToList(Notification notification);

    Page<NotificationDto> getPagedData(int pageNumber, int pageSize);




}
