package dn.mp_warehouse.domain.service.impl;


import dn.mp_warehouse.domain.WareHouseEntity;
import dn.mp_warehouse.domain.repository.ProductRepository;
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

    private final ProductRepository productRepository;


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
        var productCountOnWarehouse = warehouse.get().getCountOfProducts();
        if (productCountOnWarehouse == null) {
            throw new RuntimeException("Продукт не найден");
        }
        WareHouseEntity wareHouseEntity = warehouse.get();
        if (warehouse.isPresent()) {
            wareHouseEntity.setId(warehouse.get().getId());
            wareHouseEntity.setName(warehouse.get().getName());
            wareHouseEntity.setIsExists(true);
            wareHouseEntity.setCountOfProducts(warehouse.get().getCountOfProducts());
            log.info("WarehouseInfo is: {},{},{},{}",warehouse.get().getId(),
                    warehouse.get().getName(),warehouse.get().getIsExists(),
                    warehouse.get().getCountOfProducts());
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
