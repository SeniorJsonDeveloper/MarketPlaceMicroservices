package dn.mp_orders.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link dn.mp_orders.domain.entity.DeliveryEntity}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Delivery",description = "ДТО доставки")
public class DeliveryDto implements Serializable {

    @Schema(name = "id",description = "Уникальный идентификатор доставки")
    private String id;

    @Schema(name = "courierId",description = "Уникальный идентификатор курьера, который доставляет заказ")
    private String courierId;

    @Schema(name = "orderId",description = "Уникальный идентификатор заказа")
    private String orderId;

    @Schema(name = "address",description = "Адрес доставки")
    private String address;

    @Schema(name = "productId",description = "Уникальный идентификатор продукта")
    private String productId;

    @Schema(name = "buyerId",description = "Уникальный идентификатор покупателя")
    private String buyerId;

    @Schema(name = "shopId",description = "Уникальный идентификатор магазина")
    private String shopId;

    @Schema(name = "status",description = "Статус доставки")
    private Set<String>status;
}