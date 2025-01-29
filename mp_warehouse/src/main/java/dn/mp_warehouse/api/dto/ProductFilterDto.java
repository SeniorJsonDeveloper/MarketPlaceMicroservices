package dn.mp_warehouse.api.dto;


import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductFilterDto {

    private String country;

    private BigDecimal minPrice;

    private BigDecimal maxPrice;

    private String category;

    private String brand;

    private Integer maxSize;

    private Integer minSize;

    private PageModelDto pageModelDto = new PageModelDto(0,10);


}
