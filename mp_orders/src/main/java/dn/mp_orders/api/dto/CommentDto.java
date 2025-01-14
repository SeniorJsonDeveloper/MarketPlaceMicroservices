package dn.mp_orders.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    @JsonIgnore
    private String id;

    private String text;

    private int rating;

    @JsonIgnore
    private String orderId;

    @JsonIgnore
    private String userId;
}
