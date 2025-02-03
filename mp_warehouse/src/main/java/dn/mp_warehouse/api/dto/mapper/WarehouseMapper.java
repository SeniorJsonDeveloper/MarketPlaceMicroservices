package dn.mp_warehouse.api.dto.mapper;

import dn.mp_warehouse.api.dto.WarehouseDto;
import dn.mp_warehouse.domain.WareHouseEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface WarehouseMapper {

    WareHouseEntity toEntity(WarehouseDto warehouseDto);

    WarehouseDto toDto(WareHouseEntity wareHouseEntity);
}
