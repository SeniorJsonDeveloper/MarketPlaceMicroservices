package dn.mp_orders.domain.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

import static jakarta.persistence.FetchType.LAZY;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comments_table",schema = "orders")
public class CommentEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JsonIgnore
    private String id;

    @JsonIgnore
    private String userId;

    private String text;

    private int rating;

    @ManyToOne(cascade = CascadeType.ALL,fetch = LAZY)
    @JoinColumn(name = "order_id",insertable = false,updatable = false)
    private OrderEntity order;


}
