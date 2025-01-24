package dn.mp_orders.api.dto;


import dn.mp_orders.domain.entity.CommentEntity;
import lombok.*;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto implements Serializable {

    private String id;

    private String name;

    private String message;

    private String status;

    private BigDecimal price;

    private Double rating;

    private String userId;

    private String warehouseId;

    private List<CommentEntity> comments;

    @Override
    public String toString() {
        return "OrderDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", message='" + message + '\'' +
                ", status='" + status + '\'' +
                ", price=" + price +
                ", userId='" + userId + '\'' +
                ", warehouseId='" + warehouseId + '\'' +
                '}';
    }
}
