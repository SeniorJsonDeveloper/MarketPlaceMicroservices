package dn.mp_orders.domain.service;

import dn.mp_orders.api.dto.ListOrderDto;
import dn.mp_orders.api.dto.OrderDto;
import dn.mp_orders.domain.entity.OrderEntity;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public interface OrderService {


    ListOrderDto getAllOrders(Pageable pageable);

    List<OrderEntity> getOrderList();

    OrderDto create(OrderDto order);

    OrderDto findOrderOnWarehouse(Long id, String warehouseName) throws ExecutionException, InterruptedException, TimeoutException;

    OrderDto findOrderById(Long id);

    void delete(Long id);

    void deleteAllOrders(List<OrderEntity> orders);

    void updateOrderStatus(Long id, OrderDto order);

    default Double getTotalRating(List<OrderEntity> orders){
        if (orders == null || orders.isEmpty()) return 0.0;
        return orders.stream()
                .mapToDouble(OrderEntity::getRating)
                .average()
                .orElse(0.0);
    }

    BigDecimal getPriceOfProduct(Long productId);

    Long getCountOfProductsById(Long productId);










}
