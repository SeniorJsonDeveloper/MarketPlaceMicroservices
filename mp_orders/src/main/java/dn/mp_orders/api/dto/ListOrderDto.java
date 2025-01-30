package dn.mp_orders.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Schema(name = "ListOrderDto",description = "Список заказов")
public class ListOrderDto {

    private List<OrderDto> orderDtoList = new ArrayList<>();
}
