package dn.mp_notifications.api.dto.mapper;

import dn.mp_notifications.api.dto.NotificationDto;
import dn.mp_notifications.domain.entity.Notification;

import java.util.List;

public interface CustomNotificationMapper {

    Notification notificationDtoToNotification(NotificationDto notificationDto);

    List<NotificationDto> mapToDtoList(List<Notification> entities);

    NotificationDto mapToDto(Notification entity);


}
