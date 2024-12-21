package dn.mp_orders.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@RedisHash("orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity implements Serializable {

    @Id
    private String id;

    private String name;

    private LocalDateTime createdAt;

    private Boolean status;

    private BigDecimal price;

    private String userId;

    private String itemId;

    private String warehouseId;

    private String userNumber;

    @Override
    public String toString() {
        return "OrderEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
