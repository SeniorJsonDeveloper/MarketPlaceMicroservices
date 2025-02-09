package dn.mp_warehouse.domain.service;

import dn.mp_warehouse.api.dto.warehouse.WarehouseOutDto;

public interface WarehouseService {

    void registerWarehouse();

    WarehouseOutDto getById(String id);

    WarehouseOutDto getWarehouseByName(String warehouseName);

    //TODO:
}
