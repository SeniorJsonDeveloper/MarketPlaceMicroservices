package dn.mp_notifications.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto {


    private PageOutDto pageOut = new PageOutDto(0,10);

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

    private String status;


    private Integer pageNumber;

    private Integer pageSize;


}
