package dn.mp_warehouse.api.dto.delivery;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Data
@Schema(name = "ListDelivery",description = "Список с информацией о доставках")
public class ListDeliveryDto {

    @Schema(name = "deliveryList",description = "Список с информацией о доставке с пагинацией")
    private Page<DeliveryOutDto> deliveryList;

    public ListDeliveryDto(List<DeliveryOutDto> deliveryList,int totalElements) {
        this.deliveryList = new PageImpl<>(deliveryList);
    }
}
