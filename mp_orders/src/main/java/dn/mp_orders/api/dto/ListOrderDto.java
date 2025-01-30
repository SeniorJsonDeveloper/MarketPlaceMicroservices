package dn.mp_orders.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data

@NoArgsConstructor
@Schema(name = "ListOrderDto",description = "Список заказов")
public class ListOrderDto {

    private Page<OrderDto> orderDtoList;


    public ListOrderDto(List<OrderDto> list, int totalElements) {
        this.orderDtoList = new PageImpl<>(new ArrayList<>(list));

    }
}
