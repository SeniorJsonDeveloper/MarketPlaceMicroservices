package dn.mp_warehouse.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;

@Data
@Schema(name = "GenericList",description = "Обобщенное ДТО со списком элементов")
public class GenericListDto <T>{

    private Page<T> collection;

    public GenericListDto(Page<T> collection,int totalElements) {
        this.collection = new PageImpl<>(new ArrayList<>(totalElements));
    }
}
