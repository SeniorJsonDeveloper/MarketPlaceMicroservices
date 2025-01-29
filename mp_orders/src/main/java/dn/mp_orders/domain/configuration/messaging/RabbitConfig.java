package dn.mp_orders.domain.configuration.messaging;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class RabbitConfig {

    @Value("${rabbit.queue.name}")
    private String queueName;


    @Bean
    public Queue ordersQueue() {
        return new Queue(queueName,false);
    }


    @RabbitListener(queues = "WarehouseToOrders")
    public void receiveMessage(String message) {
        log.info("Received message: {}", message);
    }


}
