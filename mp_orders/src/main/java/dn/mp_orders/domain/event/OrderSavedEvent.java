package dn.mp_orders.domain.event;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Set;

public record OrderSavedEvent(Long orderId,
                              String status,
                              BigDecimal price,
                              Long countOfItems,
                              Boolean isExists,
                              Long productId) {

}
