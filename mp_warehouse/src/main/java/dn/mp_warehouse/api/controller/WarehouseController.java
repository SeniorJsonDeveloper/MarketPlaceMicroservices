package dn.mp_warehouse.api.controller;

import dn.mp_warehouse.api.dto.warehouse.WarehouseOutDto;
import dn.mp_warehouse.domain.service.WarehouseService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/v1/warehouse")
@RequiredArgsConstructor
@Tag(name = "Warehouse",description = "Действия со складом")
public class WarehouseController {

    private final WarehouseService warehouseService;


    @GetMapping("/{developerName}")
    @ApiResponse(description = "Операция по получению информации о складе по его названию")
    
    public WarehouseOutDto getWarehouseName(@PathVariable String developerName) {
        return warehouseService.getWarehouseByName(developerName);
    }
}




