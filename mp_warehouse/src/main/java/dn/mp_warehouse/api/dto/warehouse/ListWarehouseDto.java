package dn.mp_warehouse.api.dto.warehouse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;

@Data
@Schema(name = "ListWarehouse",description = "ДТО со списком складов")
public class ListWarehouseDto {

    Page<WarehouseOutDto> warehouses;

    public ListWarehouseDto(List<WarehouseOutDto> warehouses,int totalElements) {
        this.warehouses = new PageImpl<>(new ArrayList<>(totalElements));
    }
}
