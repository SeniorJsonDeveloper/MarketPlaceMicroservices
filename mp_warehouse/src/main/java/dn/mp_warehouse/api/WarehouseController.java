package dn.mp_warehouse.api;

import dn.mp_warehouse.api.dto.WarehouseDto;
import dn.mp_warehouse.domain.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1/warehouse")
@RequiredArgsConstructor
public class WarehouseController {

    private final WarehouseService warehouseService;


    @GetMapping("/{developerName}")
    public WarehouseDto getWarehouseName(@PathVariable String developerName) {
        return warehouseService.getWarehouseByName(developerName);
    }
}




