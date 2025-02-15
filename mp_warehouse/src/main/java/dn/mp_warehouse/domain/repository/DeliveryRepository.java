package dn.mp_warehouse.domain.repository;

import dn.mp_warehouse.domain.entity.DeliveryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<DeliveryEntity, Long> {
}