package dn.mp_notifications.api.dto.mapper;

import dn.mp_notifications.api.dto.NotificationDto;
import dn.mp_notifications.domain.entity.NotificationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NotificationMapper {

    NotificationDto toDto(NotificationEntity entity);

    NotificationEntity toEntity(NotificationDto dto);

    List<NotificationEntity> toEntityList(List<NotificationDto> notificationDtoList);

    List<NotificationDto> toDtoList(List<NotificationEntity> notificationEntityList);


}
