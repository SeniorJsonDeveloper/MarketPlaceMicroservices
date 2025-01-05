package dn.mp_orders.api.mapper;

import dn.mp_orders.api.dto.OrderDto;
import dn.mp_orders.domain.OrderEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper extends Mappable<OrderEntity, OrderDto> {
    dn.mp_notifications.api.dto.NotificationDto toDto(dn.mp_notifications.domain.entity.Notification notification);
}
