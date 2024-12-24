package dn.mp_notifications.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto {

    private String message;

    private boolean success;

    private String orderId;

    private String orderStatus;


}
