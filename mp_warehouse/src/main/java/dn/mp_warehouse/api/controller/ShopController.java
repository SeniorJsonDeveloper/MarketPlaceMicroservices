package dn.mp_warehouse.api.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/shop")
@RequiredArgsConstructor
@Tag(name = "shop",description = "Действия с магазинами")
public class ShopController {
}
