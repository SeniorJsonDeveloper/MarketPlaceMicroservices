package dn.mp_orders.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private String id;

    private String name;

    private String message;

    private String status;

    private BigDecimal price;

    private String userId;

    private String warehouseId;
}
