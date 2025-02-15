package dn.mp_warehouse.api.dto.shop;


import dn.mp_warehouse.api.dto.delivery.DeliveryOutDto;
import dn.mp_warehouse.api.dto.location.LocationOutDto;
import dn.mp_warehouse.api.dto.product.ProductOutDto;
import dn.mp_warehouse.domain.entity.*;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "ShopOut",description = "Выходящее ДТО магазина")
public class ShopOutDto {

    @Schema(name = "id",description = "Уникальный идентификатор магазина")
    private Long id;

    @Schema(name = "name",description = "Название магазина")
    private String name;

    @Schema(name = "description",description = "Описание магазина")
    private String description;

    @Schema(name = "totalCountOfDeals",description = "Суммарное количество сделок магазина")
    private Integer totalCountOfDeals;

    @Schema(name = "category",description = "Категория магазина")
    private String category;

    @Schema(name = "rating",description = "Рейтинг магазина")
    private Double rating;

    @Schema(name = "deposit",description = "Страховой депозит магазина")
    private BigDecimal deposit;

    @Schema(name = "products",description = "Список продуктов магазина")
    private List<ProductOutDto> products;

    @Schema(name = "locations",description = "Список локаций магазина")
    private List<LocationOutDto> locations;

    @Schema(name = "deliveries",description = "Список доставок магазина")
    private List<DeliveryOutDto> deliveries;

    @Schema(name = "events",description = "Список событий магазина")
    private Set<Event> events;
}
