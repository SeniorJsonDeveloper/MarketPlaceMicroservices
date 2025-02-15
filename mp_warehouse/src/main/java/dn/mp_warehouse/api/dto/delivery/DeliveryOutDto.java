package dn.mp_warehouse.api.dto.delivery;

import dn.mp_warehouse.api.dto.location.LocationInputDto;
import dn.mp_warehouse.api.dto.product.ProductOutDto;
import dn.mp_warehouse.api.dto.shop.ShopOutDto;
import dn.mp_warehouse.api.dto.warehouse.WarehouseOutDto;
import dn.mp_warehouse.domain.entity.DeliveryStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "DeliveryOut",description = "Выходящее ДТО доставки")
public class DeliveryOutDto {

    @Schema(name = "id",description = "Уникальный идентификатор доставки")
    private Long id;

    @Schema(name = "description",description = "Описание или пожелания для доставки")
    private String description;

    @Schema(name = "locationInputDto",description = "Информация о локации для доставки")
    private LocationInputDto locationInputDto;

    @Schema(name = "statusSet",description = "Список статуса доставки")
    private String status;

    @Schema(name = "productId",description = "Информация о продуктах, которые доставляются")
    private Set<ProductOutDto> productIds;

    @Schema(name = "warehouseId",description = "Информация о складах с которых доставляется товар")
    private Set<WarehouseOutDto> warehouseIds;

    @Schema(name = "shopId",description = "Информация о  магазинах, из осуществляет доставку")
    private Set<ShopOutDto> shopIds;


}
