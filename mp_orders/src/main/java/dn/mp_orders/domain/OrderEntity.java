package dn.mp_orders.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.math.BigDecimal;


@RedisHash("orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity implements Serializable {

    @Id
    private String id;

    private String name;

    @CreatedDate
    private String createdAt;

    private String status;

    private Double rating;

    private String message;

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
                ", createdAt='" + createdAt + '\'' +
                ", status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", price=" + price +
                ", userId='" + userId + '\'' +
                ", itemId='" + itemId + '\'' +
                ", warehouseId='" + warehouseId + '\'' +
                ", userNumber='" + userNumber + '\'' +
                '}';
    }
}
