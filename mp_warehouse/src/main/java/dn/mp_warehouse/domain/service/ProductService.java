package dn.mp_warehouse.domain.service;

import dn.mp_warehouse.api.dto.product.lists.ListProductRequireInfoForOrder;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.util.Map;

public interface ProductService {

    Map<String, ListProductRequireInfoForOrder> productRequireInfoForOrder(Long warehouseId);

    BigDecimal getPriceByProductId(Long id);

    Long getCountByProductId(Long id);
}
