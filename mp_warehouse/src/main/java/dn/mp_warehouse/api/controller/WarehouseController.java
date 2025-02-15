package dn.mp_warehouse.api.controller;

import dn.mp_warehouse.api.dto.warehouse.WarehouseOutDto;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/warehouse")
@RequiredArgsConstructor
@Tag(name = "Warehouse",description = "Действия со складом")
public class WarehouseController {

}




