package dn.mp_orders.api.controller;

import dn.mp_orders.api.dto.CommentDto;
import dn.mp_orders.api.dto.ListOrderDto;
import dn.mp_orders.api.dto.OrderDto;

import dn.mp_orders.domain.entity.CommentEntity;
import dn.mp_orders.domain.entity.OrderEntity;
import dn.mp_orders.domain.service.CommentService;
import dn.mp_orders.domain.service.OrderService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.slf4j.MDC;


import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    private final CommentService commentService;


    @PostMapping("/create")
    @RateLimiter(name = "order-Z",fallbackMethod = "order-V")
    private OrderDto createOrder(@RequestBody OrderDto order) {
        return orderService.save(order);
    }

    @GetMapping("/{id}")
    public OrderEntity getOrder(@PathVariable String id) {
        return orderService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable String id) {
        orderService.delete(id);
    }

    @GetMapping("/list")
    public List<OrderEntity> getOrders() {
        return orderService.getAllOrders();
    }

    @PatchMapping("/edit/{id}")
    public void editOrder(@PathVariable String id,
                          @RequestBody OrderDto order) {
        orderService.updateOrderStatus(id, order);
    }

    @PostMapping("/comment/add")
    public OrderDto setCommentForOrder(@RequestParam String orderId,
                                       @RequestBody CommentDto commentDto){
        return commentService.addCommentForOrder(orderId,commentDto);
    }

    @PatchMapping("/comment/edit")
    public void editCommentForOrder(@RequestParam String orderId,
                                    @RequestBody CommentDto commentDto){
        commentService.editComment(orderId,commentDto);
    }

}
