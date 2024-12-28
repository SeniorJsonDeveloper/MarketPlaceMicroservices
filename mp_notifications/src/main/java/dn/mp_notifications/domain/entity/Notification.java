package dn.mp_notifications.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@RedisHash("notifications")
public class Notification {

    private String id;

    private String title;

    private String userId;

    private String senderId;

    private String message;

    private LocalDateTime createdAt;


}
