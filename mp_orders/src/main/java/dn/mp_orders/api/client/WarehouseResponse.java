package dn.mp_orders.api.client;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.context.annotation.Scope;


@Schema(name = "WarehouseResponse",description = "Входящий HTTP ответ с информацией о наличии продукта на складе")
@Data
public class WarehouseResponse  {


    private String id;

    private String developerName;

    private Long countOfOrders;

    private Boolean isExists;
}
