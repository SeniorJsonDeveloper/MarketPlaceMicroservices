package dn.mp_warehouse.api.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/product")
@Tag(name = "product",description = "Действия с продуктами/товарами/предметами")
@RequiredArgsConstructor
public class ProductController {
}

