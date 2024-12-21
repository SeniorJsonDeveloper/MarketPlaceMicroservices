package dn.mp_orders.domain.service;

import dn.mp_orders.api.dto.OrderDto;
import dn.mp_orders.domain.OrderEntity;

public interface OrderService {

    OrderDto save(OrderDto order);

    OrderEntity findById(String id);

    void delete(String id);

    void deleteAllOrders();

    OrderEntity update(String id, OrderDto order);


}
