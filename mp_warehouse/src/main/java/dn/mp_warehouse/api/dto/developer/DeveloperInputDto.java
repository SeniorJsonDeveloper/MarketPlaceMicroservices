package dn.mp_warehouse.api.dto.developer;

import dn.mp_warehouse.domain.entity.Event;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "DeveloperInput",description = "Входящее ДТО производителя")
public class DeveloperInputDto {

    @Schema(name = "id",description = "Уникальный идентификатор производителя")
    private Long id;

    @Schema(name = "name",description = "Название производителя")
    private String name;

    @Schema(name = "productId",description = "Уникальный/e идентификатор/ы продукта/ов, производителя")
    private Set<Long> productIds;

    @Schema(name = "warehouseId",description = "Уникальный/e идентификатор/ы склада/ов, с которым/и работает производитель")
    private Set<Long> warehouseIds;

    @Schema(name = "category",description = "Категория производителя")
    private String category;

    @Schema(name = "shopId",description = "Уникальный/e идентификатор/ы магазина/ов , с которым/и работает производитель")
    private Set<Long> shopIds;

    @Schema(name = "events",description = "События производителя")
    private Set<Event> events;
}
