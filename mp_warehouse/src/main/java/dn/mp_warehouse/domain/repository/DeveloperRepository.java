package dn.mp_warehouse.domain.repository;

import dn.mp_warehouse.domain.entity.DeveloperEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DeveloperRepository extends JpaRepository<DeveloperEntity, Long> {
}