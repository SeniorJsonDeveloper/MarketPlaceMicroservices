package dn.mp_notifications.domain.service.impl;

import dn.mp_notifications.domain.service.UrlShortenerService;
import io.lettuce.core.RedisConnectionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class UrlShortenerServiceImpl implements UrlShortenerService {

    private static final String BASE_URL = "https://cutt.ly/api/api.php?key=1234567890&short=";

    private static final long EXPIRATION_TIME = 7;

    private final StringRedisTemplate redisTemplate;


    @Override
    public String shortenUrl(String url) {
        try {

            String shortKey = generateShortKey(url);
            redisTemplate.opsForValue().setIfAbsent(
                    shortKey, url, EXPIRATION_TIME, TimeUnit.DAYS
            );
            return BASE_URL + shortKey;
        }catch (RedisConnectionException exception){
            log.error("Redis connection error: {}", exception.getMessage());
            return null;
        }
    }

    @Override
    public Optional<String> getOriginalUrl(String shortKey) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(shortKey));
    }

    @Override
    public String generateShortKey(String url) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hash = messageDigest.digest(url.getBytes(StandardCharsets.UTF_8));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(hash).substring(0, 7);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
