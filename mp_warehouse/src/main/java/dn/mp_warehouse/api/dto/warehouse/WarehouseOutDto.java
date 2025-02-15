package dn.mp_warehouse.api.dto.warehouse;

import dn.mp_warehouse.api.dto.delivery.DeliveryOutDto;
import dn.mp_warehouse.api.dto.developer.DeveloperOutDto;
import dn.mp_warehouse.api.dto.location.LocationOutDto;
import dn.mp_warehouse.api.dto.product.ProductOutDto;
import dn.mp_warehouse.domain.entity.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Warehouse",description = "ДТО склада с товарами")
@Builder
public class WarehouseOutDto {

    @Schema(name = "id",description = "Уникальный идентификатор склада")
    private Long id;

    @Schema(name = "id",description = "Название склада")
    private String name;

    @Schema(name = "isExists",description = "Информация о наличии товара на складе")
    private Boolean isExists;

    @Schema(name = "userIds",description = "Список уникальных идентификаторов пользователей хоть раз взаимодействующих со складом")
    private Set<?> userIds;

    @Schema(name = "count",description = "Количество товаров на складе")
    private Long countOfProducts;

    @Schema(name = "products",description = "Список товаров на складе")
    private List<ProductOutDto> products;

    @Schema(name = "developers",description = "Cписок производителей")
    private List<DeveloperOutDto> developers;

    @Schema(name = "locations",description = "Список локаций на которых располагаются склады")
    private Set<LocationOutDto> locations;

    @Schema(name = "events",description = "События , происходящие со складом")
    private Set<Event> events;

    @Schema(name = "deliveries",description = "Информация о доставках на склад или со склада")
    private List<DeliveryOutDto> deliveries;




}
