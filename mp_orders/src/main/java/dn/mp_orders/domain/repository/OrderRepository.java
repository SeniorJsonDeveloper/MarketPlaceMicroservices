package dn.mp_orders.domain.repository;

import dn.mp_orders.domain.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, String> {

    boolean existsByIsActiveTrue();




}
