package dn.mp_warehouse.api.dto.developer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Data
@Schema(name = "ListDeveloper",description = "Список производителей")
public class ListDeveloperDto {

    @Schema(name = "developers",description = "Список производителей c пагинацией")
    private Page<DeveloperOutDto> developers;

    public ListDeveloperDto(List<DeveloperOutDto> developers,int totalElements) {
        this.developers = new PageImpl<>(developers);
    }
}
