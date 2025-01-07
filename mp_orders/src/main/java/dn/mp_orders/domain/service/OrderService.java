package dn.mp_orders.domain.service;
import dn.mp_orders.api.dto.OrderDto;
import dn.mp_orders.domain.OrderEntity;

import java.util.List;

public interface OrderService {

    OrderDto save(OrderDto order);

    OrderDto findById(String id);

    void delete(String id);

    void deleteAllOrders(List<OrderEntity> orders);

    void updateOrderStatus(String id, OrderDto order);

    List<OrderDto> getAllOrders();

    Double getTotalRating(List<OrderEntity> orders);

}
