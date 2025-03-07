package dn.mp_warehouse.api.dto.developer;


import dn.mp_warehouse.api.dto.location.ListLocationDto;
import dn.mp_warehouse.api.dto.product.lists.ListProductDto;
import dn.mp_warehouse.api.dto.shop.ListShopOutDto;
import dn.mp_warehouse.api.dto.warehouse.ListWarehouseDto;
import dn.mp_warehouse.domain.entity.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "DeveloperOut",description = "Выходящее ДТО производителя")
public class DeveloperOutDto {

    @Schema(name = "id",description = "Уникальный идентификатор производителя")
    private Long id;

    @Schema(name = "name",description = "Название производителя")
    private String name;

    @Schema(name = "shops",description = "Список магазинов с которыми работает производитель")
    private ListShopOutDto shops;

    @Schema(name = "locations",description = "Список локаций производителя")
    private ListLocationDto locations;

    @Schema(name = "warehouses",description = "Список складов с которыми работает производитель")
    private ListWarehouseDto warehouses;

    @Schema(name = "products",description = "Список продуктов производителя")
    private ListProductDto products;

    @Schema(name = "category",description = "Категория производителя")
    private String category;

    @Schema(name = "events",description = "События производителя")
    private Set<Event> events;
}
