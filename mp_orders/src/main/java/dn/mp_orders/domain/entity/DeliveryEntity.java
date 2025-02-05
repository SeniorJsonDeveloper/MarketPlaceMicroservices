package dn.mp_orders.domain.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(schema = "orders",name = "delivery_table")
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String courierId;

    private enum Status {
        NEW,
        DELIVERED,
        CANCELLED,
        PROCESSED;
    }

    private String orderId;

    private String address;

    private String productId;

    private String buyerId;

    private String shopId;

    private Boolean isPaid;

    private Boolean isDelivered;

    @OneToMany(mappedBy = "delivery")
    private List<OrderEntity> orders;

    @ElementCollection(fetch = FetchType.LAZY)
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Set<Status> status;

}
