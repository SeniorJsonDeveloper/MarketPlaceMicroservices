package dn.mp_warehouse.domain.service.impl;


import dn.mp_warehouse.api.dto.WarehouseDto;
import dn.mp_warehouse.domain.ProductEntity;
import dn.mp_warehouse.domain.WareHouseEntity;
import dn.mp_warehouse.domain.repository.ProductRepository;
import dn.mp_warehouse.domain.repository.WareHouseRepository;
import dn.mp_warehouse.domain.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
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
    public WarehouseDto getWarehouseByName(String developerName) {

            if (developerName == null || developerName.isBlank()) {
                throw new IllegalArgumentException("Developer name must not be null or empty");
            }

            var warehouse = wareHouseRepository.findByDeveloperName(developerName);

            if (warehouse.isPresent()) {
                WareHouseEntity originalEntity = warehouse.get();
                var countOfProducts = originalEntity.getProducts()
                        .stream()
                        .mapToLong(ProductEntity::getCountOfProducts)
                        .sum();

                WarehouseDto warehouseDto = new WarehouseDto();
                warehouseDto.setId(originalEntity.getId());
                warehouseDto.setName(developerName);
                warehouseDto.setIsExists(true);
                warehouseDto.setCountOfProducts(countOfProducts);


                log.info("WarehouseInfo is: {}, {}, {}, {}", warehouseDto.getId(),
                        warehouseDto.getName(), warehouseDto.getIsExists(),
                        warehouseDto.getCountOfProducts());

                return warehouseDto;
            } else {
                return new WarehouseDto();
            }
        }
    }
//        var warehouse = wareHouseRepository.findByDeveloperName(developerName);
//
//        if (warehouse.isPresent()) {
//            WareHouseEntity wareHouseEntity = warehouse.get();
//            var countOfProducts = warehouse.get().getProducts()
//                    .stream()
//                    .mapToLong(ProductEntity::getCountOfProducts)
//                    .sum();
//
//            wareHouseEntity.setId(warehouse.get().getId());
//            wareHouseEntity.setName(warehouse.get().getName());
//            wareHouseEntity.setIsExists(true);
//            wareHouseEntity.setCountOfProducts(countOfProducts);
//            log.info("WarehouseInfo is: {},{},{},{}", warehouse.get().getId(),
//                    warehouse.get().getName(), warehouse.get().getIsExists(),
//                    warehouse.get().getCountOfProducts());
//            return wareHouseEntity;
//        }
//
//        else {
//            return WareHouseEntity
//                    .builder()
//                    .isExists(false)
//                    .build();
//        }



