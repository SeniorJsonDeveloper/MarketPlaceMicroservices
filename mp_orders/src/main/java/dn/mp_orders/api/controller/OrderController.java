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
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
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
    @ResponseStatus(HttpStatus.CREATED)
    private OrderDto createOrder(@RequestBody OrderDto order) {
        return orderService.save(order);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderEntity getOrder(@PathVariable String id) {
        return orderService.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable String id) {
        orderService.delete(id);
    }

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderEntity> getOrders() {
        return orderService.getAllOrders();
    }

    @PatchMapping("/edit/{id}")
    @ResponseStatus(HttpStatus.RESET_CONTENT)
    public void editOrder(@PathVariable String id,
                          @RequestBody OrderDto order) {
        orderService.updateOrderStatus(id, order);
    }

    @PostMapping("/comment/add")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto setCommentForOrder(@RequestParam String orderId,
                                       @RequestBody CommentDto commentDto){
        return commentService.addCommentForOrder(orderId,commentDto);
    }

    @PatchMapping("/comment/edit")
    @ResponseStatus(HttpStatus.RESET_CONTENT)
    public void editCommentForOrder(@RequestParam String orderId,
                                    @RequestBody CommentDto commentDto){
        commentService.editComment(orderId,commentDto);
    }

}
