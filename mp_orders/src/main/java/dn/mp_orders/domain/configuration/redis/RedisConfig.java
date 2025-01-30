package dn.mp_orders.domain.configuration.redis;

import dn.mp_orders.domain.entity.OrderEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.*;

import java.time.Duration;

@Configuration
public class RedisConfig {

    @Value("${cache.redis.host}")
    private String host;

    @Value("${cache.redis.port}")
    private int port;

    @Value("${cache.cacheNames.orderList}")
    private String orders;

    @Value("${cache.cacheNames.orderById}")
    private String orderById;

    @Value("${cache.cacheNames.orderAfterCreate}")
    private String orderAfterCreate;

    @Value("${cache.cacheNames.comment}")
    private String comment;

    @Value("${cache.cacheNames.orderListWithPagination}")
    private String ordersWithPagination;





    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(host, port);
        return new  JedisConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    public <F,S> RedisTemplate<F, S> redisTemplate() {
        RedisTemplate<F, S> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        return redisTemplate;
    }


    @Bean
    public RedisCacheManagerBuilderCustomizer cacheManagerBuilderCustomizer() {
        return (builder) -> builder
                .withCacheConfiguration(orderAfterCreate, RedisCacheConfiguration.defaultCacheConfig()
                        .entryTtl(Duration.ofMinutes(5)))
                .withCacheConfiguration(orderById,  RedisCacheConfiguration.defaultCacheConfig()
                        .entryTtl(Duration.ofMinutes(10)))
                .withCacheConfiguration(orders, RedisCacheConfiguration.defaultCacheConfig()
                        .entryTtl(Duration.ofMinutes(10)))
                .withCacheConfiguration(comment,
                        RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofDays(14)))
                .withCacheConfiguration(ordersWithPagination, RedisCacheConfiguration.defaultCacheConfig()
                        .entryTtl(Duration.ofMinutes(20))
                                .serializeValuesWith(RedisSerializationContext.SerializationPair
                                        .fromSerializer(new GenericJackson2JsonRedisSerializer())));
    }

    @Bean
    public GenericJackson2JsonRedisSerializer jackson2JsonRedisSerializer() {
        return new GenericJackson2JsonRedisSerializer();
    }

    @Bean
    public RedisCacheConfiguration redisCacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(10))
                .disableCachingNullValues()
                .serializeValuesWith(RedisSerializationContext.SerializationPair
                                .fromSerializer(new Jackson2JsonRedisSerializer<>(OrderEntity.class)));
    }

    @Bean
    public RedisCacheManager redisCacheManager(){
        RedisCacheConfiguration redisCacheConfiguration = redisCacheConfiguration();
        return RedisCacheManager.builder(jedisConnectionFactory())
                .cacheDefaults(redisCacheConfiguration)
                .build();
    }

}
