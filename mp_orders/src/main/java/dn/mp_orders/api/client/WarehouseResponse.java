package dn.mp_orders.api.client;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.springframework.context.annotation.Scope;


@Data
@Builder
@Schema(name = "WarehouseResponse",description = "Входящий HTTP ответ с информацией о наличии продукта на складе")
@Scope(scopeName = "request")
public class WarehouseResponse {

    private String id;

    private String developerName;

    private Boolean isExists;
}
