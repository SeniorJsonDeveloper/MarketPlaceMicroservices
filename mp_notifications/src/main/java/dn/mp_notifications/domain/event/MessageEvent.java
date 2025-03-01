package dn.mp_notifications.domain.event;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class MessageEvent {

    private Long id;

    private String message;

    private String status;

    private BigDecimal amount;

    private String orderId;

    public MessageEvent(Long id, String message, String status, BigDecimal zero) {
        this.amount = BigDecimal.ZERO;
    }
}
