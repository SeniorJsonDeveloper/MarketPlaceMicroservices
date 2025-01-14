package dn.mp_orders.domain.configuration.redis;

import dn.mp_orders.domain.entity.OrderEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;

@ReadingConverter
@RequiredArgsConstructor
@Component
public class RedisReadConverter implements Converter<byte[], OrderEntity> {

    private final GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer;


    @Override
    public OrderEntity convert(byte[] source) {
        return (OrderEntity) genericJackson2JsonRedisSerializer.deserialize(source);
    }
}
