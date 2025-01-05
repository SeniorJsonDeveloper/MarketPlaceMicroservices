package dn.mp_notifications.api.dto.mapper;

import dn.mp_notifications.domain.entity.Notification;
import dn.mp_notifications.api.dto.NotificationDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NotificationMapper {

    @Mapping(source = "orderId",target = "orderId",ignore = false)
    @Mapping(source = "status",target = "status")
    NotificationDto mapToDto(Notification entity);

    List<NotificationDto> mapToDtoList(List<Notification> entities);

    List<Notification> mapToDtoEntity(List<NotificationDto> entities);





}
