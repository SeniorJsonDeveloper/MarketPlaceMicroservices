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
@RedisHash(value = "comments")
@Entity
@Table(name = "comments_table",schema = "orders")
public class CommentEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JsonIgnore
    private String id;

    @JsonIgnore
    private String orderId;

    @JsonIgnore
    private String userId;

    @Size(min = 1,max = 255,message = "Text cant be empty")
    @Positive(message = "Text cant be negative")
    private String text;

    @Min(value = 1,message = "Rating cant be <1")
    @Max(value = 5,message = "Rating cant be >5")
    private int rating;


}
