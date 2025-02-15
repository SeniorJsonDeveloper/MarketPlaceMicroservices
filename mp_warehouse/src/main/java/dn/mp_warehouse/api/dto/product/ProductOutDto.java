package dn.mp_warehouse.api.dto.product;

import dn.mp_warehouse.api.dto.delivery.DeliveryOutDto;
import dn.mp_warehouse.api.dto.developer.DeveloperOutDto;
import dn.mp_warehouse.api.dto.location.LocationOutDto;
import dn.mp_warehouse.api.dto.warehouse.WarehouseOutDto;
import dn.mp_warehouse.domain.entity.*;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "ProductOut",description = "Выходящее ДТО товара")
public class ProductOutDto {

    @Schema(name = "id",description = "Уникальный идентификатор товара")
    private Long id;

    @Schema(name = "productName",description = "Название товара")
    private String productName;

    @Schema(name = "cost",description = "Ценник товара")
    private BigDecimal cost;

    @Schema(name = "description",description = "Описание товара")
    private String description;

    @Schema(name = "category",description = "Категория товара")
    private String category;

    @Schema(name = "brand",description = "Бренд товара")
    private String brand;

    @Schema(name = "count",description = "Количество товара")
    private Long count;

    @Schema(name = "buyerId",description = "Уникальный идентификатор покупателя")
    private String buyerId;

    @Schema(name = "warehouse",description = "Информация о складе, где находится товар")
    private WarehouseOutDto warehouseId;

    @Schema(name = "events",description = "Уникальный идентификатор производителя")
    private Set<Event> events;

    @Schema(name = "location",description = "Информация о местоположении товара")
    private LocationOutDto location;

    @Schema(name = "delivery",description = "Информация о доставке товара")
    private DeliveryOutDto delivery;

    @Schema(name = "developer",description = "Информация о производителе")
    private DeveloperOutDto developer;
}
