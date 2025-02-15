package dn.mp_warehouse.api.dto.warehouse;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "WarehouseInput",description = "Входящее ДТО склада")
@Builder
public class WarehouseInputDto {

    @Schema(name = "id",description = "Уникальный идентификатор склада")
    private Long id;

    @Schema(name = "id",description = "Название склада")
    private String name;

    @Schema(name = "developerName",description = "Название производителя/магазина")
    private String developerName;
}
