package dn.mp_warehouse.api.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductInfoForBucket {

    @JsonProperty("Название товара")
    private String productName;

    @JsonProperty("Цена товара")
    private BigDecimal productPrice;

    @JsonProperty("Количество")
    private Long count;
}
