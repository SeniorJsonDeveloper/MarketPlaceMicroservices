package dn.mp_warehouse.api.dto.location;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "LocationInput",description = "Входящее ДТО локации")
public class LocationInputDto {

    @Schema(name = "id",description = "Уникальный идентификатор локации")
    private Long id;

    @Schema(name = "country",description = "Страна")
    private String country;

    @Schema(name = "city",description = "Город")
    private String city;

    @Schema(name = "street",description = "Улица")
    private String street;

    @Schema(name = "house",description = "Дом")
    private String house;

    @Schema(name = "flat",description = "Квартира")
    private String flat;
}
