package dn.mp_warehouse.api.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "ListProduct",description = "ДТО со списком продуктов")
public class ListProductDto {

    @Schema(name = "products",description = "Список продуктов")
    private List<ProductOutDto> products = new ArrayList<>();

}