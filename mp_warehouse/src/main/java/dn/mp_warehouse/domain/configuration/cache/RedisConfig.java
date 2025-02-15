package dn.mp_warehouse.domain.configuration.cache;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RedisConfig {

    @Value("${app.redis.host}")
    private String host;

    @Value("${app.redis.port}")
    private int port;

    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(host, port);
        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    public <F,S>RedisTemplate<F,S> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory){
        RedisTemplate<F,S> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        return redisTemplate;
    }

    @ConditionalOnExpression("'${app.cache.cacheType}'.equals('redis')")
    public CacheManager cacheManager(CacheConfig cacheConfig,
                                     JedisConnectionFactory jedisConnectionFactory){
        var redisConfig = RedisCacheConfiguration.defaultCacheConfig();
        Map<String,RedisCacheConfiguration> redisCacheMap = new HashMap<>();
        cacheConfig.getCaches().forEach((cacheName,cacheProperties)->{
            redisCacheMap.put(cacheName,redisConfig.entryTtl(cacheProperties.getExpiry()));
        });
        return RedisCacheManager.builder(jedisConnectionFactory)
                .cacheDefaults(redisConfig)
                .withInitialCacheConfigurations(redisCacheMap)
                .build();
    }

}
