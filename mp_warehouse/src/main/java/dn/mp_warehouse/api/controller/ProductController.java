package dn.mp_warehouse.api.controller;
import dn.mp_warehouse.api.dto.product.lists.ListProductRequireInfoForOrder;
import dn.mp_warehouse.domain.repository.ProductRepository;
import dn.mp_warehouse.domain.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
@Tag(name = "product",description = "Действия с продуктами/товарами/предметами")
@Slf4j
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{id}/price")
    public BigDecimal getPriceByProductId(@PathVariable Long id) {
        return productService.getPriceByProductId(id);
    }

    @GetMapping("/{id}/count")
    public Long getCountByProductId(@PathVariable Long id) {
        return productService.getCountByProductId(id);
    }

    @GetMapping("/productInfo/{warehouseId}")
    public Map<String, ListProductRequireInfoForOrder> productRequireInfoForOrder(@PathVariable Long warehouseId) {
        return productService.productRequireInfoForOrder(warehouseId);
    }
}



