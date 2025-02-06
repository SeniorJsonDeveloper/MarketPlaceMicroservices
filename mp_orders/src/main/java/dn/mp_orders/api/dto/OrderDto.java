package dn.mp_orders.api.dto;
import dn.mp_orders.domain.entity.CommentEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "OrderDto",description = "ДТО заказа")
public class OrderDto implements Serializable {

    @Schema(name = "id",description = "Входящий идентификатор заказа")
    private String id;

    @NotBlank
    @Schema(name = "name",description = "Название заказа")
    private String name;

    @Size(max = 255)
    @Schema(name = "message",description = "Сообщение , которое передается в брокер сообщений")
    private String message;


    @NotBlank
    @Schema(name = "status",description = "Статус заказа")
    private String status;

//    @NotBlank
//    @DecimalMin(value = "0.01", message = "Price must be at least 0.01")
//    @DecimalMax(value = "10000.00", message = "Price must not be greater than 10000.00")
//    @Digits(integer = 5, fraction = 2, message = "Price must have up to 5 integer digits and 2 decimal places")
    @Schema(name = "price",description = "Цена заказа")
    private BigDecimal price;

//    @PositiveOrZero
    @Schema(name = "rating",description = "Рейтинг заказа")
    private Double rating;

//    @NotNull
    @Schema(name = "developerName",description = "Уникальный идентификатор пользователя, совершившего заказ")
    private String developerName;

//    @NotNull
    @Schema(name = "warehouseId",description = "Уникальный идентификатор склада, которому принадлежал товар")
    private String warehouseId;

    @Schema(name = "isExists",description = "Информация о наличии товара на складе")
    private Boolean isExists;

    @Schema(name = "comments",description = "Комментарии к заказу")
    private List<CommentEntity> comments;

    @Schema(name = "countOfProducts",description = "Количество товаров в заказе")
    private Long countOfProducts;

    @Override
    public String toString() {
        return "OrderDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", message='" + message + '\'' +
                ", status='" + status + '\'' +
                ", price=" + price +
                ", developerName='" + developerName + '\'' +
                ", warehouseId='" + warehouseId + '\'' +
                '}';
    }
}
