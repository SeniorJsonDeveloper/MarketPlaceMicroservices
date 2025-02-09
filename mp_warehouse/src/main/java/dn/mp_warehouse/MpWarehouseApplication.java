package dn.mp_warehouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableKafka
@EnableAsync
@EnableScheduling
@EnableRetry
@EnableTransactionManagement
@EnableDiscoveryClient
public class MpWarehouseApplication {

    public static void main(String[] args) {
        SpringApplication.run(MpWarehouseApplication.class, args);
    }

}
