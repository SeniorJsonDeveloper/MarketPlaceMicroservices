package dn.mp_notifications.domain.util;

import dn.mp_notifications.domain.entity.Notification;
import dn.mp_notifications.domain.service.SenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DataInitializer {

//    private final SenderService senderService;
//
//    @Override
//    public void run(String... args) throws Exception {
//        for(long i = 0; i <= 50; i++) {
//            Notification notification = new Notification();
//            notification.setId(UUID.randomUUID().toString());
//            notification.setTitle("Title"+i);
//            senderService.addToList(notification);
//
//        }
//    }
}
