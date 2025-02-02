package dn.mp_warehouse.api.dto;


import dn.mp_warehouse.domain.ProductEntity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WarehouseDto {

    private String id;

    private String name;

    private List<ProductEntity> products;

    private String developerName;

    private Boolean isExists;

    private Long countOfProducts;

}
