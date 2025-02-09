package dn.mp_warehouse.api.dto.shop;


import dn.mp_warehouse.domain.entity.ProductEntity;
import dn.mp_warehouse.domain.entity.WareHouseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "ShopOut",description = "Выходящее ДТО магазина")
public class ShopOutDto {

    @Schema(name = "id",description = "Уникальный идентификатор магазина")
    private String id;

    @Schema(name = "name",description = "Название магазина")
    private String name;

    @Schema(name = "description",description = "Описание магазина")
    private String description;

    @Schema(name = "totalCountOfDeals",description = "Суммарное количество сделок магазина")
    private Integer totalCountOfDeals;

    @Schema(name = "rating",description = "Рейтинг магазина")
    private Double rating;

    @Schema(name = "deposit",description = "Страховой депозит магазина")
    private BigDecimal deposit;

    @Schema(name = "products",description = "Список продуктов магазина")
    private List<ProductEntity> products;

    @Schema(name = "warehouses",description = "Список складов, на которых располагаются товары магазина")
    private List<WareHouseEntity> warehouses;
}
