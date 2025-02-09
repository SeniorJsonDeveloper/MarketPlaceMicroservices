package dn.mp_warehouse.api.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "PageModel",description = "ДТО с параметрами пагинации")
public class PageModelDto {

    @Schema(name = "pageNumber",description = "Номер страницы")
    private Integer pageNumber = 0;

    @Schema(name = "pageSize",description = "Количество элементов умещающихся на странице")
    private Integer pageSize = 10;

    public PageRequest getPageRequest() {
        return PageRequest.of(pageNumber, pageSize);
    }
}
