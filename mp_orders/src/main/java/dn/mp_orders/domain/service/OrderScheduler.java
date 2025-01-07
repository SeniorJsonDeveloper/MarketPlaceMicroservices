package dn.mp_orders.domain.service;
import dn.mp_orders.api.mapper.OrderMapper;
import dn.mp_orders.domain.OrderEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderScheduler {

    private final OrderService orderService;

    private final OrderMapper orderMapper;

    @Scheduled(fixedRate = 5000)
    public void cleanAlreadyMadeOrders() {
        List<OrderEntity> orders = orderMapper.fromDto(orderService.getAllOrders());
        orderService.deleteAllOrders(orders);

    }

    @Scheduled(fixedRate = 5000)
    public void getReviewAllOrders(){
        List<OrderEntity> orders = orderMapper.fromDto(orderService.getAllOrders());
    }
}
