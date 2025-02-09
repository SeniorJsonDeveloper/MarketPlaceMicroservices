package dn.mp_warehouse.api.dto.mapper;

import dn.mp_warehouse.api.dto.product.ProductOutDto;
import dn.mp_warehouse.domain.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper extends Mappable<ProductEntity, ProductOutDto> {


}
