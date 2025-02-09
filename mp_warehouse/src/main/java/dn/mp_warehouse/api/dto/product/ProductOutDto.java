package dn.mp_warehouse.api.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "ProductOut",description = "Выходящее ДТО товара")
public class ProductOutDto {

    @Schema(name = "id",description = "Уникальный идентификатор товара")
    private String id;

    @Schema(name = "productName",description = "Название товара")
    private String productName;

    @Schema(name = "cost",description = "Ценник товара")
    private BigDecimal cost;

    @Schema(name = "cost",description = "Описание товара")
    private String description;

    @Schema(name = "cost",description = "Категория товара")
    private String category;

    @Schema(name = "cost",description = "Бренд товара")
    private String brand;

    @Schema(name = "cost",description = "Страна производитель товара")
    private String country;

    @Schema(name = "cost",description = "Количество товара")
    private Long count;

    @Schema(name = "cost",description = "Уникальный идентификатор покупателя")
    private String buyerId;

    @Schema(name = "cost",description = "Уникальный идентификатор производителя")
    private String sellerId;
}
