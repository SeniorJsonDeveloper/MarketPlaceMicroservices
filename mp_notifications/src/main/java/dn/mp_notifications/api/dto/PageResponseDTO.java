package dn.mp_notifications.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResponseDTO <T>{

    private long totalElements;

    private Integer totalPages;

    private List<T> content;
}
