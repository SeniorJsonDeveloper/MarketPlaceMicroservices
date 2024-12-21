package dn.mp_orders.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderScheduled {


    private final OrderService orderService;



    @Scheduled(fixedRate = 3600)
    public void scheduled() {
        orderService.deleteAllOrders();

    }
}
