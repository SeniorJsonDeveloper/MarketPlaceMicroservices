package dn.mp_orders.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders_table",
        schema = "orders",
        indexes = {
        @Index(name = "idx_orderentity_productid",
                columnList = "productId")
})
public class OrderEntity implements Serializable {

    @Id
    @Column(nullable = false,updatable = false,unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private String status;

    @OneToOne(fetch = FetchType.EAGER)
    private BucketEntity bucket;

    private Double rating;

    private Long countOfProducts;

    private Long productId;

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


    @OneToMany(mappedBy = "order",cascade = CascadeType.MERGE)
    private List<CommentEntity> comments;

}
