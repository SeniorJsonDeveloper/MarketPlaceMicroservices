package dn.mp_orders.domain.configuration.redis;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

@Component("customRedisKeyGenerator")
public class RedisKeyGenerator implements KeyGenerator {

    @Override
    public Object generate(Object target, Method method, Object... params) {

        String className = target.getClass().getSimpleName();
        String methodName = method.getName();
        String paramsString = Arrays.toString(params);

        return className + "." + methodName + "(" + paramsString + ")";
    }
}
