package dn.mp_orders.domain.service;
import dn.mp_orders.api.dto.ListOrderDto;
import dn.mp_orders.api.dto.OrderDto;
import dn.mp_orders.domain.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public interface OrderService {


    ListOrderDto getAllOrders(Pageable pageable);

    List<OrderEntity> getOrderList();

    OrderDto create(OrderDto order);

    OrderDto findOrderOnWarehouse(String id, String warehouseName) throws ExecutionException, InterruptedException;

    OrderDto findOrderById(String id);

    void delete(String id);

    void deleteAllOrders(List<OrderEntity> orders);

    void updateOrderStatus(String id, OrderDto order);

    Double getTotalRating(List<OrderEntity> orders);









}
