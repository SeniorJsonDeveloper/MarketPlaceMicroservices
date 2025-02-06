package dn.mp_orders.domain.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;


//@RedisHash("orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders_table",schema = "orders")
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderEntity implements Serializable {

    @Id
    private String id;

    private String name;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
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

    private Boolean isExists;

    @OneToMany(mappedBy = "order")
    private List<CommentEntity> commentIds;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private DeliveryEntity delivery;

    @Override
    public String toString() {
        return "OrderEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", price=" + price +
                ", developerName='" + userId + '\'' +
                ", itemId='" + itemId + '\'' +
                ", warehouseId='" + warehouseId + '\'' +
                ", userNumber='" + userNumber + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OrderEntity order = (OrderEntity) o;
        return Objects.equals(getId(), order.getId()) && Objects.equals(getName(), order.getName()) && Objects.equals(getCreatedAt(), order.getCreatedAt()) && Objects.equals(getStatus(), order.getStatus()) && Objects.equals(getRating(), order.getRating()) && Objects.equals(getMessage(), order.getMessage()) && Objects.equals(getPrice(), order.getPrice()) && Objects.equals(getUserId(), order.getUserId()) && Objects.equals(getItemId(), order.getItemId()) && Objects.equals(getWarehouseId(), order.getWarehouseId()) && Objects.equals(getUserNumber(), order.getUserNumber()) && Objects.equals(getIsActive(), order.getIsActive()) && Objects.equals(getIsExists(), order.getIsExists()) && Objects.equals(getCommentIds(), order.getCommentIds());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getCreatedAt(), getStatus(), getRating(), getMessage(), getPrice(), getUserId(), getItemId(), getWarehouseId(), getUserNumber(), getIsActive(), getIsExists(), getCommentIds());
    }
}
