package dn.mp_orders.domain.configuration.redis;

import dn.mp_orders.domain.entity.OrderEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;

@Component
@WritingConverter
@RequiredArgsConstructor
public class RedisWrireConverter implements Converter<OrderEntity, byte[]> {

    private final GenericJackson2JsonRedisSerializer jackson2JsonRedisSerializer;


    @Override
    public byte[] convert(OrderEntity source) {
        return jackson2JsonRedisSerializer.serialize(source);
    }

    public RedisWrireConverter() {
        jackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        jackson2JsonRedisSerializer.serialize(new OrderEntity());
    }
}
