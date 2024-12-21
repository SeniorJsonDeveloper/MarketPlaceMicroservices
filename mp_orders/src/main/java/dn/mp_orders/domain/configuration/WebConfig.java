package dn.mp_orders.domain.configuration;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class WebConfig {

    @Value("${web.integration.warehouseUrl}")
    private String warehouseUrl;

    @Value("${web.integration.paymentsUrl}")
    private String paymentsUrl;

    @Value("${web.integration.pricesUrl}")
    private String pricesUrl;

    @Bean
    public RestClient restClient(){
        return RestClient.builder().build();
    }
}
