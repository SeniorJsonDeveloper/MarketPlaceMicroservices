package dn.mp_warehouse.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;

@Data
@NoArgsConstructor
public class ListDto<T> {

    private Page<T> list;

    public ListDto(int totalElements) {
        this.list = new PageImpl<>(new ArrayList<>(totalElements));
    }
}
