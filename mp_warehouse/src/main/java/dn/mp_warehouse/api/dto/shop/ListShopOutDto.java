package dn.mp_warehouse.api.dto.shop;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;

@Data
@Schema(name = "ListShopOut",description = "ДТО со списком магазинов")
public class ListShopOutDto{

    @Schema(name = "shops",description = "Список магазинов с пагинацией")
    private Page<ShopOutDto> shops;

    public ListShopOutDto(List<ShopOutDto> list, int totalElements) {
        this.shops = new PageImpl<>(new ArrayList<>(list));

    }
}
