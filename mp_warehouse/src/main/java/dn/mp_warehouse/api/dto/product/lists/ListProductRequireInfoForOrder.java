package dn.mp_warehouse.api.dto.product.lists;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListProductRequireInfoForOrder {


    @JsonProperty("Информация о заказе")
    private  List<ProductRequireInfoForOrder> productListResponse = new ArrayList<>();


}
