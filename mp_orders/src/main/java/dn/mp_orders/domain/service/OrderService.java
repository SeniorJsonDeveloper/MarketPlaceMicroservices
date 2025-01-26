package dn.mp_orders.domain.service;
import dn.mp_orders.api.dto.CommentDto;
import dn.mp_orders.api.dto.ListOrderDto;
import dn.mp_orders.api.dto.OrderDto;
import dn.mp_orders.domain.entity.CommentEntity;
import dn.mp_orders.domain.entity.OrderEntity;

import java.util.List;
import java.util.Set;

public interface OrderService {

    OrderDto save(OrderDto order);

    OrderEntity findById(String id);

    void delete(String id);

    void deleteAllOrders(List<OrderEntity> orders);

    void updateOrderStatus(String id, OrderDto order);

    List<OrderEntity> getAllOrders();

    Double getTotalRating(List<OrderEntity> orders);

    void getAvgRatingByComments();

    void cleanAllOrders();






}
