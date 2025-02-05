package dn.mp_orders.api.mapper;

import dn.mp_orders.api.dto.OrderDto;
import dn.mp_orders.domain.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {


    OrderDto toDto(OrderEntity order);

    @Mapping(target = "delivery",ignore = true)
    OrderEntity toEntity(OrderDto orderDto);

}
