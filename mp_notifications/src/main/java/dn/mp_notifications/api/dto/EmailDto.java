package dn.mp_notifications.api.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Email",description = "ДТО сообщения для отправки на электронную почту")
public class EmailDto {

    @Schema(name = "phoneNumber",description = "Номер телефона получателя")
    private String phoneNumber;

    @Schema(name = "senderId",description = "Уникальный идентификатор отправителя")
    private String senderId;

    @Schema(name = "message",description = "Сообщение")
    private Object message;
}
