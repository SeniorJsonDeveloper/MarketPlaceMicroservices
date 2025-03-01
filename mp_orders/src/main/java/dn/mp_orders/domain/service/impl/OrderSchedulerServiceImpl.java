package dn.mp_orders.domain.service.impl;

import dn.mp_orders.api.dto.CommentDto;
import dn.mp_orders.domain.entity.OrderEntity;
import dn.mp_orders.domain.repository.CommentRepository;
import dn.mp_orders.domain.repository.OrderRepository;
import dn.mp_orders.domain.service.CommentService;
import dn.mp_orders.domain.service.OrderSchedulerService;
import dn.mp_orders.domain.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.TimeZone;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderSchedulerServiceImpl implements OrderSchedulerService {

    private final CommentService commentService;

    private final OrderRepository orderRepository;

    private final CommentRepository commentRepository;

    private final ApplicationContext applicationContext;


    @Scheduled(cron = "0 0 0 * * *")
    @Override
    public void cleanAllOrders() {
        try {
            OrderService proxy = applicationContext.getBean(OrderService.class);
            List<OrderEntity> orders = proxy.getOrderList().stream()
                    .toList();
            if (!orders.isEmpty()) {
                log.warn("No orders found. Skipping cache cleaning.");
            }
            orderRepository.deleteAll(orders);
            log.info("Cache cleaned: {}", orders.stream().map(OrderEntity::getId).toList());
        }catch (Exception e){
            log.error("Failed to clean cache: {}", e.getMessage(), e);
        }
    }

    @Override
    @Scheduled(fixedRate = 3000L)
    public void getAvgRatingByComments() {
        try {
            var comments = commentService.findAllComments()
                    .stream()
                    .mapToDouble(CommentDto::getRating)
                    .average()
                    .orElse(0.0);
            var count = commentRepository.count();
            var avgRating = comments/count;

            if (avgRating == 0) {
                log.warn("No comments found. Skipping cache cleaning.");
                cleanCache();
            }
            if (avgRating > 0.0 && avgRating < 5.0) {
                log.info("Avg Rating: {}", avgRating);
            }
            else {
                log.warn("Avg Rating: {}", avgRating);
                log.warn("Rating must be between 0.0 and 5.0");
                log.warn("Skipping rating update");
            }
        }catch (Exception e){
            log.error("Failed to get rating by comments: {}", e.getMessage(), e);
            throw new RuntimeException("Rating from comment service failed");
        }

    }

    @Scheduled(cron = "0 0 9 * * Mon")
    @CacheEvict(value = "comments", allEntries = true)
    public void cleanCache(){}

}
