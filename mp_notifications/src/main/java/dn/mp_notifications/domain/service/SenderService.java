package dn.mp_notifications.domain.service;
import dn.mp_notifications.api.dto.MessageDto;
import dn.mp_notifications.domain.entity.Notification;
import dn.mp_notifications.api.dto.NotificationDto;
import org.springframework.data.domain.Page;
import java.util.List;



public interface SenderService {


    Page<NotificationDto> getPagedData(int pageNumber, int pageSize);

    Page<Notification> findAllNotificationsWithPaging(NotificationDto notificationDto);

    List<Notification> findAllNotifications();

    Long getNotificationsCount();

    NotificationDto findNotificationById(String id);

    void addToList(Notification notification);

    String generateCode();

    NotificationDto sendNotification(MessageDto messageDto, String orderId);

    String sendSmsCode(String phoneNumber);

    void deleteNotifications(List<Notification> notifications);





}
