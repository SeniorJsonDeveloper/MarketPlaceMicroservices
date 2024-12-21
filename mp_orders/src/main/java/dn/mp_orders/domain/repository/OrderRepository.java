package dn.mp_orders.domain.repository;

import dn.mp_orders.domain.OrderEntity;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<OrderEntity, String> {
}
