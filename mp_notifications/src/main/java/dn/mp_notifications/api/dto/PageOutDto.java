package dn.mp_notifications.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageOutDto {

    private Integer pageNumber;

    private Integer pageSize;

    public PageRequest getPageRequest() {
        return PageRequest.of(pageNumber, pageSize);
    }
}
