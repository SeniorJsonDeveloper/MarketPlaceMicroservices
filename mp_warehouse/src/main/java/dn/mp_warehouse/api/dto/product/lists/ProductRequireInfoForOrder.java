package dn.mp_warehouse.api.dto.product.lists;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import dn.mp_warehouse.api.dto.product.ProductInfoForBucket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequireInfoForOrder {

    @JsonIgnore
    private Set<Long> productIds;

    @JsonProperty("Товар, Цена")
    private List<ProductInfoForBucket> productInfoForBuckets;

    @JsonProperty("Всего товаров")
    private Long totalCount;

    @JsonProperty("Итоговая цена")
    private BigDecimal totalPrice;


}
