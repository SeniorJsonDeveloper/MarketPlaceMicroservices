package dn.mp_orders.api.mapper;

import dn.mp_orders.api.dto.CommentDto;
import dn.mp_orders.domain.entity.CommentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper extends Mappable<CommentEntity, CommentDto> {
}
