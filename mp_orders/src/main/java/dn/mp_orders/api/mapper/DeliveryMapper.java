package dn.mp_orders.api.mapper;
import dn.mp_orders.api.dto.DeliveryDto;
import dn.mp_orders.domain.entity.DeliveryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DeliveryMapper extends Mappable<DeliveryEntity, DeliveryDto> {
}
