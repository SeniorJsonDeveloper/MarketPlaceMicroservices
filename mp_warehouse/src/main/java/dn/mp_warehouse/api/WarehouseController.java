package dn.mp_warehouse.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import dn.mp_warehouse.domain.ProductEntity;
import dn.mp_warehouse.domain.WareHouseEntity;
import dn.mp_warehouse.domain.repository.ProductRepository;
import dn.mp_warehouse.domain.repository.WareHouseRepository;
import dn.mp_warehouse.domain.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("api/v1/warehouse")
@RequiredArgsConstructor
public class WarehouseController {

    private final WarehouseService warehouseService;


    @GetMapping("/{developerName}")
    public WareHouseEntity getWarehouseName(@PathVariable String developerName) {
        return warehouseService.getWarehouseByName(developerName);
    }
}




