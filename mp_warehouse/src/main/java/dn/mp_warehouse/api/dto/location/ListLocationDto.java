package dn.mp_warehouse.api.dto.location;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import java.util.ArrayList;
import java.util.List;

@Data
@Schema(name = "ListLocation", description = "Список с локациями")
public class ListLocationDto {

    @Schema(name = "locationList",description = "Список локаций с пагинацией")
    private Page<LocationOutDto> locationList;

    public ListLocationDto(List<LocationOutDto> locationList, int totalElements) {
        this.locationList = new PageImpl<>(new ArrayList<>(locationList));
    }
}
