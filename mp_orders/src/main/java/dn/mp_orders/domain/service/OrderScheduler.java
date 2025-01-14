package dn.mp_orders.domain.service;
import dn.mp_orders.api.mapper.OrderMapper;
import dn.mp_orders.domain.entity.CommentEntity;
import dn.mp_orders.domain.entity.OrderEntity;
import dn.mp_orders.domain.repository.CommentRepository;
import dn.mp_orders.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderScheduler {

    private final OrderService orderService;

    private final OrderRepository orderRepository;

    private final CommentService commentService;

    private final CommentRepository commentRepository;

    @Scheduled(cron = "0 0 * * * *")
    public void getAvgRatingByComments(){
        List<CommentEntity> comments = (List<CommentEntity>) commentRepository.findAll();
        Double rating = commentService.getRatingByComments(comments);
        log.info("Average rating of orders: {}", rating);
    }

    @Scheduled(fixedRate = 500)
    public void cleanCache(){
        List<OrderEntity> orders = orderService.getAllOrders();
        log.info("Deleted orders: {}", orders);
        orderRepository.deleteAll();
    }


}
