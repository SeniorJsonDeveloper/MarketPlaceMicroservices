package dn.mp_warehouse.api;

import dn.mp_warehouse.domain.WareHouseEntity;
import dn.mp_warehouse.domain.repository.WareHouseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("api/v1/warehouse")
@RequiredArgsConstructor
public class WarehouseController {

    private final WareHouseRepository wareHouseRepository;


    @GetMapping("/{developerName}")
    public WareHouseEntity getWarehouseName(@PathVariable String developerName){
        var warehouse =  wareHouseRepository.findByDeveloperName(developerName);
        if (warehouse.isPresent()){
            WareHouseEntity wareHouse = warehouse.get();
            wareHouse.setIsExists(true);
            log.info("Statement of warehouse: {}",wareHouse.getIsExists());
        }
        else {
            WareHouseEntity wareHouse = new WareHouseEntity();
            wareHouse.setIsExists(false);
            log.info("Statement of warehouse: {}",wareHouse.getIsExists());
            return null;
        }
        return warehouse.get();

    }
}
