package dn.mp_orders;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication(exclude = {org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration.class})
@EnableScheduling
@EnableCaching
@EnableKafka
@EnableAsync
@EnableRabbit
public class MpOrdersApplication {

    public static void main(String[] args) {
        SpringApplication.run(MpOrdersApplication.class, args);
    }

}
