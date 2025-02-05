package dn.mp_orders.api.controller;

import dn.mp_orders.api.dto.CommentDto;
import dn.mp_orders.api.dto.ListOrderDto;
import dn.mp_orders.api.dto.OrderDto;
import dn.mp_orders.domain.service.CommentService;
import dn.mp_orders.domain.service.OrderService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@RestController
@RequiredArgsConstructor
@Tag(name = "Order",description = "Действия с заказом")
public class OrderController {

    private static final String ADD_COMMENT = "/api/v1/order/comment/add";

    private static final String EDIT_COMMENT = "/api/v1/order/comment/edit";

    private static final String EDIT_ORDER = "/api/v1/order/edit/{id}";

    private static final String DELETE_ORDER = "/api/v1/order/delete/{id}";

    private static final String CREATE_ORDER = "/api/v1/order/create";

    private static final String GET_ORDER = "/api/v1/order/{id}";

    private static final String GET_LIST_OF_ORDERS = "/api/v1/order/list";


    private final OrderService orderService;

    private final CommentService commentService;


    @PostMapping(CREATE_ORDER)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponse(description = "Операция по созданию заказа",responseCode = "201")
    public OrderDto createOrder(@Valid @RequestBody OrderDto order) {
        return orderService.create(order);
    }

    @GetMapping(GET_ORDER)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponse(description = "Операция по поиску заказа на складе",responseCode = "200")
    public OrderDto getOrder(@PathVariable String id,
                             @RequestParam(required = false) String developerName) throws ExecutionException,
                                                                                         InterruptedException,
                                                                                          TimeoutException {
        return orderService.findOrderOnWarehouse(id,developerName);
    }

    @DeleteMapping(DELETE_ORDER)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponse(description = "Операция по  удалению заказа",responseCode = "204")
    public void deleteOrder(@PathVariable String id) {
        orderService.delete(id);
    }


    @GetMapping(GET_LIST_OF_ORDERS)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponse(description = "Операция по получению списка заказов с пагинацией",responseCode = "200")
    public ListOrderDto getOrders(@RequestParam(defaultValue = "0") int pageNumber,
                                  @RequestParam(defaultValue = "10") int pageSize) {
        return orderService.getAllOrders(PageRequest.of(pageNumber,pageSize));

    }

    @PatchMapping(EDIT_ORDER)
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ApiResponse(description = "Изменение заказа",responseCode = "200")
    public void editOrder(@PathVariable String id,
                          @RequestBody OrderDto order) {
        orderService.updateOrderStatus(id, order);
    }

    @PostMapping(ADD_COMMENT)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponse(description = "Добавление комментария для заказа",responseCode = "200")
    public OrderDto setCommentForOrder(@RequestParam String orderId,
                                       @Validated @RequestBody CommentDto commentDto){
        return commentService.addCommentForOrder(orderId,commentDto);
    }

    @PatchMapping(EDIT_COMMENT)
    @ResponseStatus(HttpStatus.RESET_CONTENT)
    @ApiResponse(description = "Редактирование комментария для заказа",responseCode = "201")
    public void editCommentForOrder(@RequestParam String orderId,
                                    @RequestBody CommentDto commentDto){
        commentService.editComment(orderId,commentDto);
    }

}
