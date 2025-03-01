package dn.mp_orders.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Comment",description = "Входящий комментарий для заказа")
public class CommentDto {

    @JsonIgnore
    @Schema(name = "id",description = "Уникальный идентификатор комментария")
    private String id;

    @Size(min = 1,max = 255,message = "Text cant be empty")
    @Schema(name = "text",description = "Текст")
    private String text;

    @Min(value = 1,message = "Rating cant be <1")
    @Max(value = 5,message = "Rating cant be >5")
    @Schema(name = "rating",description = "Рейтинг заказа")
    private int rating;

    @NotBlank
    @Schema(name = "orderId",description = "Уникальный идентификатор заказа, для которого добавляется комментарий")
    private Long orderId;

    @JsonIgnore
    @Schema(name = "developerName",description = "Уникальный идентификатор пользователя, оставившего комментарий")
    private String userId;
}
