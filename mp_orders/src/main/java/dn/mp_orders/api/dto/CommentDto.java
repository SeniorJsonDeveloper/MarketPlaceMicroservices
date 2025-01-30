package dn.mp_orders.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
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

    @Size(min = 1,max = 255,message = "Text cant be empty")
    private String text;

    @Min(value = 1,message = "Rating cant be <1")
    @Max(value = 5,message = "Rating cant be >5")
    private int rating;

    @NotBlank
    private String orderId;

    @JsonIgnore
    private String userId;
}
