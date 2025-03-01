package dn.mp_notifications.domain.service;

import dn.mp_notifications.api.dto.NotificationDto;
import dn.mp_notifications.domain.entity.NotificationEntity;
import dn.mp_notifications.domain.event.MessageEvent;
import org.springframework.data.domain.Page;

import java.util.List;



public interface SenderService {


    Page<NotificationDto> getPagedData(int pageNumber, int pageSize);

    Page<NotificationEntity> findAllNotificationsWithPaging(NotificationDto notificationDto);

    List<NotificationEntity> findAllNotifications();

    Long getNotificationsCount();

    NotificationDto findNotificationById(Long id);

    void addToList(NotificationEntity notificationEntity);

    String generateCode();

    NotificationDto sendNotification(MessageEvent messageDto, String orderId);

    String sendSmsCode(String phoneNumber);

    void deleteNotifications(List<NotificationEntity> notificationEntities);







}
