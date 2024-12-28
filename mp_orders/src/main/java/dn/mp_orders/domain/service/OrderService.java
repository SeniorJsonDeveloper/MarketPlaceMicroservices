package dn.mp_orders.domain.service;

import dn.mp_orders.api.dto.OrderDto;
import dn.mp_orders.domain.OrderEntity;

import java.io.InputStream;

public interface OrderService {

    OrderDto save(OrderDto order);

    OrderDto findById(String id);

    void delete(String id);

    void deleteAllOrders();

    OrderDto update(String id, OrderDto order);

    Iterable<OrderDto> getAllOrders();

}
