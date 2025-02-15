package dn.mp_warehouse.api.dto.shop;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "ShopInput",description = "Входящее ДТО магазина")
public class ShopInputDto {

    @Schema(name = "id",description = "Уникальный идентификатор магазина")
    private Long id;

    @Schema(name = "name",description = "Название магазина")
    private String name;

    @Schema(name = "category",description = "Категория магазина")
    private String category;

    @Schema(name = "description",description = "Описание магазина")
    private String description;

    @Schema(name = "totalCountOfDeals",description = "Суммарное количество сделок магазина")
    private Integer totalCountOfDeals;

    @Schema(name = "rating",description = "Рейтинг магазина")
    private Double rating;

    @Schema(name = "deposit",description = "Страховой депозит магазина")
    private BigDecimal deposit;

}
