package dn.mp_orders.domain.configuration.messaging;


import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Value("${rabbit.queue.name}")
    private String queueName;

    @Value("${rabbit.delay_queue.name}")
    private String delayQueueName;


    @Bean
    public Queue ordersQueue() {
        return new Queue(queueName,false);
    }

    @Bean
    public Queue ordersDelayQueue() {
        return new Queue(delayQueueName,false);
    }


}
