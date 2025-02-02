package dn.mp_warehouse.domain.repository;

import dn.mp_warehouse.domain.WareHouseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WareHouseRepository extends JpaRepository<WareHouseEntity, String>,
                                             JpaSpecificationExecutor<WareHouseEntity> {


    Optional<WareHouseEntity> findByDeveloperName(String developerName);

//    @Query("SELECT SUM(p.count) FROM ProductEntity p WHERE p.warehouse.id = :warehouseId")
//    Long findTotalProductCountByWarehouse(@Param("warehouseId") String warehouseId);


}
