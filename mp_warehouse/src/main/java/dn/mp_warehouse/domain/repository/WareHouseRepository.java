package dn.mp_warehouse.domain.repository;

import dn.mp_warehouse.domain.WareHouseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface WareHouseRepository extends JpaRepository<WareHouseEntity, String>, JpaSpecificationExecutor<WareHouseEntity> {
}