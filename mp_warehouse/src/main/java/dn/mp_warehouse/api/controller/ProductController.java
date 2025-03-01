package dn.mp_warehouse.api.controller;

import dn.mp_warehouse.domain.entity.ProductEntity;
import dn.mp_warehouse.domain.repository.ProductRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("api/v1/product")
@RequiredArgsConstructor
@Tag(name = "product",description = "Действия с продуктами/товарами/предметами")
public class ProductController {

    private final ProductRepository productRepository;

    @GetMapping("/{id}")
    public BigDecimal getPriceByProductId(@PathVariable Long id){
        return productRepository.getPriceById(id)
                .orElseThrow(()->new RuntimeException("Product not found"));
    }

    @GetMapping("/{id}/count")
    public Long getCountByProductId(@PathVariable Long id){
        return productRepository.getCountById(id)
                .orElseThrow(()->new RuntimeException("Product not found"));
    }
}

