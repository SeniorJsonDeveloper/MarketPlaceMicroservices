package dn.mp_orders.api.client;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "WarehouseResponse",description = "Входящий HTTP ответ с информацией о наличии продукта на складе")
public class WarehouseResponse  {

    @Schema(name = "id",description = "Уникальный идентификатор склада")
    private String id;

    @Schema(name = "developerName",description = "Имя производителя товара")
    private String developerName;

    @Schema(name = "countOfProduct",description = "Количество товара на складе")
    private Long countOfProducts;

    @Schema(name = "isExists",description = "Наличие товара на складе")
    private Boolean isExists;
}
