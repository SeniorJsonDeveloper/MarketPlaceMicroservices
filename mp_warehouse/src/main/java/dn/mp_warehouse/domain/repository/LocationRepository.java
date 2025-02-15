package dn.mp_warehouse.domain.repository;

import dn.mp_warehouse.domain.entity.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<LocationEntity, Long> {
}