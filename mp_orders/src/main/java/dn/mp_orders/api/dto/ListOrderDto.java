package dn.mp_orders.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class ListOrderDto {

    private List<OrderDto> orderDtoList = new ArrayList<>();
}
