package dn.mp_warehouse.api.dto.mapper;

import dn.mp_warehouse.api.dto.ProductOutDto;
import dn.mp_warehouse.domain.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {

    ProductEntity toEntity(ProductOutDto productOutDto);

    ProductOutDto toDto(ProductEntity productEntity);


}
