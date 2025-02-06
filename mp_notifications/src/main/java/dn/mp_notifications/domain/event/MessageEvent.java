package dn.mp_notifications.domain.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageEvent {

    private String id;

    private String message;

    private String status;

    private BigDecimal amount;

    private String orderId;

    public MessageEvent(String id, String message, String status, BigDecimal zero) {
        this.amount = BigDecimal.ZERO;
    }
}
