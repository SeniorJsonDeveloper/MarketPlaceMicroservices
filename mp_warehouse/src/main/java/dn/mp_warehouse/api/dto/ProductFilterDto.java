package dn.mp_warehouse.api.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "ProductFilter",description = "ДТО для фильтрации товаров по заданным параметрам")
public class ProductFilterDto {


    @Schema(name = "country",description = "Страна производитель товара")
    private String country;

    @Schema(name = "minPrice",description = "Минимальная товара")
    private BigDecimal minPrice;

    @Schema(name = "maxPrice",description = "Максимальная товара")
    private BigDecimal maxPrice;

    @Schema(name = "category",description = "Категория товара")
    private String category;

    @Schema(name = "brand",description = "Бренд товара")
    private String brand;

    @Schema(name = "maxSize",description = "Максимальный размер товара ")
    private Integer maxSize;

    @Schema(name = "minSize",description = "Минимальный размер товара")
    private Integer minSize;

    @Schema(name = "PageModel",description = "ДТО с параметрами пагинации")
    private PageModelDto pageModelDto = new PageModelDto(0,10);


}
