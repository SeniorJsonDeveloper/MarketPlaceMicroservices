package dn.mp_warehouse.api.dto.warehouse;

import dn.mp_warehouse.api.dto.product.ProductOutDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Warehouse",description = "ДТО склада с товарами")
public class WarehouseOutDto {

    @Schema(name = "id",description = "Уникальный идентификатор склада")
    private String id;

    @Schema(name = "id",description = "Название склада")
    private String name;

    @Schema(name = "products",description = "Список товаров на складе")
    private List<ProductOutDto> products;

    @Schema(name = "developerName",description = "Название производителя/магазина")
    private String developerName;

    @Schema(name = "userIds",description = "Список уникальных идентификаторов пользователей хоть раз взаимодействующих со складом")
    private Set<?> userIds;

    @Schema(name = "isExists",description = "Информация о наличии товара на складе")
    private Boolean isExists;

    @Schema(name = "count",description = "Количество товаров на складе")
    private Long countOfProducts;

    @Schema(name = "Список уникальных идентификаторов магазинов, принадлежащих складу")
    private Set<?> shopIds;



}
