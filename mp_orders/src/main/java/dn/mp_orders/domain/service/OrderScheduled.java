package dn.mp_orders.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderScheduled {


    private final OrderService orderService;



    public void scheduled() {
        orderService.deleteAllOrders();

    }
}
