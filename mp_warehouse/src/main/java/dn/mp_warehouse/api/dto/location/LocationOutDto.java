package dn.mp_warehouse.api.dto.location;


import dn.mp_warehouse.api.dto.delivery.DeliveryOutDto;
import dn.mp_warehouse.api.dto.developer.DeveloperOutDto;
import dn.mp_warehouse.api.dto.shop.ShopOutDto;
import dn.mp_warehouse.api.dto.warehouse.WarehouseOutDto;
import dn.mp_warehouse.domain.entity.DeliveryEntity;
import dn.mp_warehouse.domain.entity.DeveloperEntity;
import dn.mp_warehouse.domain.entity.ShopEntity;
import dn.mp_warehouse.domain.entity.WareHouseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "LocationOut",description = "Выходящее ДТО локации")
public class LocationOutDto {

    @Schema(name = "id",description = "Уникальный идентификатор локации")
    private Long id;

    @Schema(name = "country",description = "Страна")
    private String country;

    @Schema(name = "city",description = "Город")
    private String city;

    @Schema(name = "street",description = "Улица")
    private String street;

    @Schema(name = "developer",description = "Уникальный идентификатор магазина")
    private DeveloperOutDto developer;

    @Schema(name = "deliveries",description = "Список доставок конкретной локации")
    private Set<DeliveryOutDto> deliveries;

    @Schema(name = "shops",description = "Список магазинов конкретной локации")
    private List<ShopOutDto> shops;

    @Schema(name = "warehouse",description = "Информации о складе на локации")
    private WarehouseOutDto warehouse;
}
