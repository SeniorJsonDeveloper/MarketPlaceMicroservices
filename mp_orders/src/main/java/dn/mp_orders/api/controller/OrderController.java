package dn.mp_orders.api.controller;

import dn.mp_orders.api.dto.OrderDto;

import dn.mp_orders.domain.service.OrderService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/create")
    @RateLimiter(name = "order-Z",fallbackMethod = "order-V")
    private OrderDto createOrder(@RequestBody OrderDto order) {
        return orderService.save(order);
    }

    @GetMapping("/{id}")
    public OrderDto getOrder(@PathVariable String id) {
        return orderService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable String id) {
        orderService.delete(id);
    }

    @GetMapping("/list")
    public List<OrderDto> getOrders() {
        return orderService.getAllOrders();
    }

    @PatchMapping("/edit/{id}")
    public void editOrder(@PathVariable String id,
                          @RequestBody OrderDto order) {
        orderService.updateOrderStatus(id, order);
    }

}
