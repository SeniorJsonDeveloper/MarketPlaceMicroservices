package dn.mp_warehouse.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageModelDto {

    private Integer pageNumber = 0;

    private Integer pageSize = 10;

    public PageRequest getPageRequest() {
        return PageRequest.of(pageNumber, pageSize);
    }
}
