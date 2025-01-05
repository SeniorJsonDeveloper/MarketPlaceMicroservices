package dn.mp_notifications.api.dto;

import dn.mp_notifications.domain.entity.Notification;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto implements Serializable {

    private String id;

    private String message;

    private String name;

    private String status;

    private String secretCode;

    private String phoneNumber;

    private Double rating;

    private BigDecimal price;

    @Override
    public String toString() {
        return "OrderDto{" +
                "id='" + id + '\'' +
                ", message='" + message + '\'' +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", secretCode='" + secretCode + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", rating=" + rating +
                ", price=" + price +
                '}';
    }
}
