package dn.mp_warehouse.api.dto.delivery;

import dn.mp_warehouse.api.dto.location.LocationInputDto;
import dn.mp_warehouse.domain.entity.DeliveryStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "LocationInput",description = "Входящее ДТО доставки")
public class DeliveryInputDto {

    @Schema(name = "id",description = "Уникальный идентификатор доставки")
    private Long id;

    @Schema(name = "description",description = "Описание или пожелания для доставки")
    private String description;

    @Schema(name = "locationInputDto",description = "Информация о локации для доставки")
    private LocationInputDto locationInputDto;

    @Schema(name = "statusSet",description = "Список статусов для доставки")
    private Set<DeliveryStatus> statusSet;

    @Schema(name = "productId",description = "Уникальный идентификатор продукта, который надо доставить")
    private Set<Long> productIds;

    @Schema(name = "warehouseId",description = "Уникальный идентификатор склада, с которого доставляется товар")
    private Set<Long> warehouseIds;

    @Schema(name = "shopId",description = "Уникальный/e идентификатор магазина , который осуществляет доставку")
    private Set<Long> shopIds;
}
