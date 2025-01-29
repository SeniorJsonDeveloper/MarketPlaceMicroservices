package dn.mp_orders.domain.service.impl;

import dn.mp_orders.api.dto.OrderDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderSavedEvent {

    private OrderDto orderDto;
}
