package dn.mp_warehouse.domain.repository;

import dn.mp_warehouse.domain.entity.WareHouseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WareHouseRepository extends JpaRepository<WareHouseEntity, Long>,
                                             JpaSpecificationExecutor<WareHouseEntity> {


    Optional<WareHouseEntity> findByDevelopers_Name(String developersName);






}
