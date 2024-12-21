package dn.mp_orders.api.controller;

import dn.mp_orders.api.dto.OrderDto;
import dn.mp_orders.domain.OrderEntity;
import dn.mp_orders.domain.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/create")
    private OrderDto createOrder(@RequestBody OrderDto order) {
        return orderService.save(order);
    }

    @GetMapping("/{id}")
    public OrderEntity getOrder(@PathVariable String id) {
        return orderService.findById(id);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteOrder(@PathVariable String id) {
        orderService.delete(id);
    }

}
