package dn.mp_notifications.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "PageModel",description = "ДТО с параметрами пагинации")
public class NotificationDto {

    @Schema(name = "pageOut",description = "ДТО с параметрами пагинации")
    private PageModelDto pageOut = new PageModelDto(0,10);

    @Schema(name = "id",description = "Уникальный идентификатор уведомления")
    private String id;

    @Schema(name = "title",description = "Текст уведомления")
    private String title;

    @JsonIgnore
    @Schema(name = "developerName",description = "Уникальный идентификатор получателя уведомления")
    private String userId;

    @JsonIgnore
    @Schema(name = "senderId",description = "Уникальный идентификатор отправителя уведомления")
    private String senderId;

    @Schema(name = "message",description = "Сообщение в брокер сообщений")
    private String message;

    @JsonFormat(pattern = "yyyy:Mm:dd")
    @Schema(name = "createdAt",description = "Время создания уведомления")
    private LocalDateTime createdAt;

    @Schema(name = "orderId",description = "Уникальный идентификатор заказа")
    private String orderId;

    @Schema(name = "status",description = "Статус заказа")
    private String status;

    @Schema(name = "PageModelDto",description = "ДТО с параметрами пагинации")
    PageModelDto pageModelDto = new PageModelDto(1,10);


}
