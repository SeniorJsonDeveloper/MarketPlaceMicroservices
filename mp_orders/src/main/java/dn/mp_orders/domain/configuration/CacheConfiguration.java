package dn.mp_orders.domain.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@ConfigurationProperties(prefix = "app.cache")
@Component
public class CacheConfiguration {

    private final List<String> cacheNames = new ArrayList<>();

    private final Map<String,CacheProperties> caches = new HashMap<>();

    private final CacheType cacheType = CacheType.REDIS;

    @Data
    public static class CacheProperties {
        private Duration expiry = Duration.ZERO;
    }

    public enum CacheType{
        REDIS
    }



}
