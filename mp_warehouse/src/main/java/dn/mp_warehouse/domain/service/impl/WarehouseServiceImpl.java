package dn.mp_warehouse.domain.service.impl;


import dn.mp_warehouse.domain.WareHouseEntity;
import dn.mp_warehouse.domain.repository.WareHouseRepository;
import dn.mp_warehouse.domain.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class WarehouseServiceImpl implements WarehouseService {

    private final WareHouseRepository wareHouseRepository;


    @Override
    public void registerWarehouse() {

    }

    @Override
    public WareHouseEntity getById(String id) {
        return null;
    }

    @Override
    public WareHouseEntity getWarehouseByName(String developerName) {
        var warehouse = wareHouseRepository.findByDeveloperName(developerName);
        WareHouseEntity wareHouseEntity = warehouse.get();
        if (warehouse.isPresent()) {
            wareHouseEntity.setId(warehouse.get().getId());
            wareHouseEntity.setName(warehouse.get().getName());
            wareHouseEntity.setIsExists(true);
            log.info("WarehouseInfo is: {},{},{}",warehouse.get().getId(),
                    warehouse.get().getName(),warehouse.get().getIsExists());
            return wareHouseEntity;
        }
        else {
            wareHouseEntity.setId(null);
            wareHouseEntity.setName(null);
            wareHouseEntity.setIsExists(false);
            return new WareHouseEntity();
        }

    }
}
