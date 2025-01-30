package dn.mp_orders.api.mapper;

import dn.mp_orders.api.dto.OrderDto;
import dn.mp_orders.domain.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {

    OrderDto toDto(OrderEntity order);

    OrderEntity toEntity(OrderDto orderDto);


}
