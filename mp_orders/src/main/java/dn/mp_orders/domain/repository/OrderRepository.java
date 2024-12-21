package dn.mp_orders.domain.repository;

import dn.mp_orders.domain.OrderEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNullApi;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<OrderEntity, String> {



}
