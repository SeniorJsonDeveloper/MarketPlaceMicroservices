package dn.mp_orders.domain.service.impl;

import dn.mp_orders.domain.entity.OrderEntity;
import dn.mp_orders.domain.repository.OrderRepository;
import dn.mp_orders.domain.service.OrderSchedulerService;
import dn.mp_orders.domain.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderSchedulerServiceImpl implements OrderSchedulerService {

    private final OrderService orderService;

    private final OrderRepository orderRepository;

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
    public void getAvgRatingByComments() {

    }

//    @Override
//    @Scheduled(fixedRate = 5000)
//    public void getAvgRatingByComments() {
//        try {
//
//           var ratingByComments =  orderService.getTotalRating(orderRepository.findAll());
//           if (ratingByComments == null || ratingByComments.isNaN()) {
//               ratingByComments = 0.0;
//           }
//
//           log.info("Average Rating: {}",ratingByComments);
//        } catch (Exception e) {
//            log.error("Failed to calculate average rating: {}", e.getMessage(), e);
//        }
//
//    }
}
