package dn.mp_notifications.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import dn.mp_notifications.api.dto.MessageDto;
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
@RedisHash("notificationZ")
public class Notification {

    private String id;

    private String title;

    @JsonIgnore
    private String userId;

    @JsonIgnore
    private String senderId;

    private String message;


    @JsonFormat(pattern = "yyyy:Mm:dd")
    private LocalDateTime createdAt;

    private String orderId;

    private MessageDto messageDto;

    private String status;


    private Integer pageNumber;

    private Integer pageSize;


}
