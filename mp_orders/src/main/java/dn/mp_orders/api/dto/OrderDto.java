package dn.mp_orders.api.dto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dn.mp_orders.domain.entity.CommentEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "OrderDto",description = "ДТО заказа")
public class OrderDto implements Serializable {

    private String id;

    @NotBlank
    private String name;

    @Size(max = 255)
    private String message;


    @NotBlank
    private String status;

//    @NotBlank
//    @DecimalMin(value = "0.01", message = "Price must be at least 0.01")
//    @DecimalMax(value = "10000.00", message = "Price must not be greater than 10000.00")
//    @Digits(integer = 5, fraction = 2, message = "Price must have up to 5 integer digits and 2 decimal places")
    private BigDecimal price;

//    @PositiveOrZero
    private Double rating;

//    @NotNull
    private String userId;

//    @NotNull
    private String warehouseId;

    private Boolean isExists;

    private List<CommentEntity> comments;

    private Long countOfProducts;

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
