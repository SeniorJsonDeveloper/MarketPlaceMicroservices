package dn.mp_orders.domain.service;

import dn.mp_orders.api.dto.ListOrderDto;
import dn.mp_orders.api.dto.OrderDto;
import dn.mp_orders.domain.entity.OrderEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public interface OrderService {


    ListOrderDto getAllOrders(Pageable pageable);

    List<OrderEntity> getOrderList();

    OrderDto create(OrderDto order);

    OrderDto findOrderOnWarehouse(String id, String warehouseName) throws ExecutionException, InterruptedException, TimeoutException;

    OrderDto findOrderById(String id);

    void delete(String id);

    void deleteAllOrders(List<OrderEntity> orders);

    void updateOrderStatus(String id, OrderDto order);

    default Double getTotalRating(List<OrderEntity> orders){
        if (orders == null || orders.isEmpty()) return 0.0;
        return orders.stream()
                .mapToDouble(OrderEntity::getRating)
                .average()
                .orElse(0.0);
    }









}
