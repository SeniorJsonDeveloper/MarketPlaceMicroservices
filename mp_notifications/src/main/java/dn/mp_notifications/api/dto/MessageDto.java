package dn.mp_notifications.api.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "MessageDto",description = "ДТО нотификации для отправки на телефон пользователя")
public class MessageDto implements Serializable {

    @Schema(name = "id",description = "Уникальный идентификатор уведомления")
    private String id;

    @Schema(name = "message",description = "Текст сообщения")
    private String message;

    @Schema(name = "status",description = "Статус заказа")
    private String status;

    @Schema(name = "secretCode",description = "Секретный код")
    private String secretCode;

    @Schema(name = "phoneNumber",description = "Номер телефон получателя")
    private String phoneNumber;

    @Override
    public String toString() {
        return "MessageDto{" +
                "id='" + id + '\'' +
                ", message='" + message + '\'' +
                ", status='" + status + '\'' +
                ", secretCode='" + secretCode + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
