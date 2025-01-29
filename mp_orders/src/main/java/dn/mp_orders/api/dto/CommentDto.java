package dn.mp_orders.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "CommentDto",description = "Входящий комментарий для заказа")
public class CommentDto {

    @JsonIgnore
    private String id;

    @NotBlank
    @Size(min = 5,max = 255)
    private String text;

    @PositiveOrZero
    @NotNull
    private int rating;

    @JsonIgnore
    private String orderId;

    @JsonIgnore
    private String userId;
}
