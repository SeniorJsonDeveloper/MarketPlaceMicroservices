package dn.mp_warehouse.domain.configuration;

import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class WebConfig {

   @Bean
    public OkHttpClient okHttpClient(){
       return new OkHttpClient().newBuilder()
               .callTimeout(10, TimeUnit.SECONDS)
               .readTimeout(10, TimeUnit.SECONDS)
               .connectTimeout(10, TimeUnit.SECONDS)
               .writeTimeout(10, TimeUnit.SECONDS)
               .retryOnConnectionFailure(true)
               .followRedirects(false)
               .build();
   }
}
