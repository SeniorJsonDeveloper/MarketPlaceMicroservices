package dn.mp_warehouse.api.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WarehouseDto {

    private String id;

    private String name;

    private List<ProductOutDto> products;

    private String developerName;

    private List<?> userIds;

    private Boolean isExists;

    private Long countOfProducts;

}
