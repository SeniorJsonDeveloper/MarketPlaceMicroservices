package dn.mp_notifications.domain.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationDto {

    private String id;

    private String title;

    private String userId;

    private String senderId;

    private String message;

    private LocalDateTime createdAt;
}
