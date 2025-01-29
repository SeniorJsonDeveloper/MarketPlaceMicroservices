package dn.mp_warehouse.api.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProductListDto {

    private List<ProductOutDto> products;
}
