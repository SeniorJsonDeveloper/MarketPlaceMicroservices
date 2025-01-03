package dn.mp_notifications.api.dto.mapper;

import dn.mp_notifications.domain.entity.Notification;
import dn.mp_notifications.domain.entity.NotificationDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    @Mapping(source = "orderId",target = "orderId",ignore = false)
    NotificationDto mapToDto(Notification entity);
}
