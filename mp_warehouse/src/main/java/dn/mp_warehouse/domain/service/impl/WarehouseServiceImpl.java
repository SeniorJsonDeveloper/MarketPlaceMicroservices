package dn.mp_warehouse.domain.service.impl;
import dn.mp_warehouse.api.dto.mapper.WarehouseMapper;
import dn.mp_warehouse.api.dto.warehouse.WarehouseOutDto;
import dn.mp_warehouse.api.exception.WarehouseNotFoundException;
import dn.mp_warehouse.domain.entity.ProductEntity;
import dn.mp_warehouse.domain.entity.WareHouseEntity;
import dn.mp_warehouse.domain.repository.WareHouseRepository;
import dn.mp_warehouse.domain.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@RequiredArgsConstructor
@Service
public class WarehouseServiceImpl implements WarehouseService {

    private final WareHouseRepository wareHouseRepository;

    private final WarehouseMapper warehouseMapper;


    @Override
    public void registerWarehouse() {

    }

    @Override
    public WarehouseOutDto getById(String id) {
        return warehouseMapper.toDto(wareHouseRepository.findById(id)
                .orElseThrow(()->new WarehouseNotFoundException("Склад не найден")));
    }

    @Override
    public WarehouseOutDto getWarehouseByName(String developerName) {

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
                originalEntity.setCountOfProducts(countOfProducts);

                WarehouseOutDto warehouseOutDto = new WarehouseOutDto();
                warehouseOutDto.setId(originalEntity.getId());
                warehouseOutDto.setDeveloperName(originalEntity.getDeveloperName());
                warehouseOutDto.setIsExists(true);
                warehouseOutDto.setCountOfProducts(originalEntity.getCountOfProducts());


                log.info("WarehouseInfo is: {}, {}, {}, {}", warehouseOutDto.getId(),
                        warehouseOutDto.getName(), warehouseOutDto.getIsExists(),
                        warehouseOutDto.getCountOfProducts());

                return warehouseOutDto;
            } else {
                return new WarehouseOutDto();
            }
        }
    }




