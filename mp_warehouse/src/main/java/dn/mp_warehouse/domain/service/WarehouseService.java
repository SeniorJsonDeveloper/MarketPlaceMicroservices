package dn.mp_warehouse.domain.service;

import dn.mp_warehouse.api.dto.WarehouseDto;
import dn.mp_warehouse.domain.WareHouseEntity;

public interface WarehouseService {

    void registerWarehouse();

    WareHouseEntity getById(String id);

    WarehouseDto getWarehouseByName(String warehouseName);

    //TODO:
}
