package dn.mp_orders.api.dto;


import lombok.*;


import java.math.BigDecimal;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private String id;

    private String name;

    private String message;

    private String status;

    private BigDecimal price;

    private String userId;

    private String warehouseId;
}
