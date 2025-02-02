package dn.mp_warehouse.domain.service;

import dn.mp_warehouse.domain.WareHouseEntity;

public interface WarehouseService {

    void registerWarehouse();

    WareHouseEntity getById(String id);

    WareHouseEntity getWarehouseByName(String warehouseName);

    //TODO:
}
