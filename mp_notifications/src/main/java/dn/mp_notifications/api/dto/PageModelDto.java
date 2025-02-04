package dn.mp_notifications.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "PageModel",description = "ДТО с параметрами пагинации")
public class PageModelDto {

    @Schema(name = "pageNumber",description = "Номер страницы")
    private Integer pageNumber;

    @Schema(name = "pageSize",description = "Количество элементов умещающихся на странице")
    private Integer pageSize;

    public PageRequest getPageRequest() {
        return PageRequest.of(pageNumber, pageSize);
    }
}
