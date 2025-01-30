package dn.mp_orders.domain.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;


//@RedisHash("orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders_table",schema = "orders")
public class OrderEntity implements Serializable {

    @Id
    private String id;

    private String name;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Transient
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private String status;

    private Double rating;

    private String message;

    private BigDecimal price;

    @JsonIgnore
    private String userId;

    @JsonIgnore
    private String itemId;

    @JsonIgnore
    private String warehouseId;

    @JsonIgnore
    private String userNumber;

    @JsonIgnore
    private Boolean isActive;

    @Transient
    @Enumerated(EnumType.STRING)
    private Set<String> commentIds;

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
