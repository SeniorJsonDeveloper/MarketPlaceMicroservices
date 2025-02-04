package dn.mp_warehouse.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "cost",description = "Ценник товара")
public class ProductListDto {

    @Schema(name = "cost",description = "Ценник товара")
    private List<ProductOutDto> products = new ArrayList<>();

}