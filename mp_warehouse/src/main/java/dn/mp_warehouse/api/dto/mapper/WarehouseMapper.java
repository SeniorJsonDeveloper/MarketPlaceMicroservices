package dn.mp_warehouse.api.dto.mapper;

import dn.mp_warehouse.api.dto.warehouse.WarehouseOutDto;
import dn.mp_warehouse.domain.entity.WareHouseEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface WarehouseMapper extends Mappable<WareHouseEntity, WarehouseOutDto> {


}
