package dn.mp_orders.api.mapper;

import dn.mp_orders.api.dto.OrderDto;
import dn.mp_orders.domain.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper extends Mappable<OrderEntity, OrderDto> {

default public OrderDto mapToDto(OrderEntity order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setName(order.getName());
        orderDto.setMessage(order.getMessage());
        orderDto.setStatus(order.getStatus());
        orderDto.setPrice(order.getPrice());
        orderDto.setWarehouseId(order.getWarehouseId());
        var comments = commentRepository.findAllById(order.getCommentIds());
        orderDto.setComments((List<CommentEntity>) comments);
        return orderDto;

}
