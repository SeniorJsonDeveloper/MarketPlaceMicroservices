package dn.mp_notifications.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto {

    @Builder.Default
    private PageOutDto pageOut = new PageOutDto(0,10);

    private String id;

    private String title;

    private String userId;

    private String orderId;

    private String status;

    private String senderId;

    private String message;

    private LocalDateTime createdAt;
}
