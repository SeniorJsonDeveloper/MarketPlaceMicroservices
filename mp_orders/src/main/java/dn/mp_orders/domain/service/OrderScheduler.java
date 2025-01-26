package dn.mp_orders.domain.service;
import ch.qos.logback.classic.Logger;
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
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderScheduler {

    private final OrderService orderService;

    private final OrderRepository orderRepository;

    private final CommentService commentService;

    private final CommentRepository commentRepository;

    private static final Logger log = (Logger) org.slf4j.LoggerFactory.getLogger(OrderScheduler.class);

    @Scheduled(cron = "0 0 * * * *")
    public void getAvgRatingByComments(){
        try {
            List<CommentEntity> comments = (List<CommentEntity>) commentRepository.findAll();
            if (comments.isEmpty()) {
                log.warn("No comments found. Skipping average rating calculation.");
                return;
            }
            Double rating = commentService.getRatingByComments(comments);
            log.info("Average Rating: {}", rating);
        } catch (Exception e) {
            log.error("Failed to calculate average rating: {}", e.getMessage(), e);
        }
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void cleanAllOrders(){
        try {
            List<OrderEntity> orders = orderService.getAllOrders();
            if (orders.isEmpty()) {
                log.warn("No orders found. Skipping cache cleaning.");
            }
            orderRepository.deleteAll(orders);
            log.info("Cache cleaned: {}", orders.stream().map(OrderEntity::getId).toList());
        }catch (Exception e){
            log.error("Failed to clean cache: {}", e.getMessage(), e);
        }
    }



}
