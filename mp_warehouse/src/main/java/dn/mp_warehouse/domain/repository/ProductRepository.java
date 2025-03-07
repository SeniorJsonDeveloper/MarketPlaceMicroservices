package dn.mp_warehouse.domain.repository;

import dn.mp_warehouse.domain.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long>, JpaSpecificationExecutor<ProductEntity> {


    Optional<ProductEntity> findByProductName(String name);

    @Query("SELECT p.price FROM ProductEntity p WHERE p.id = :id")
    Optional<BigDecimal> getPriceById(@Param("id") Long id);

    @Query("SELECT p.countOfProducts FROM ProductEntity p where p.id = :id")
    Optional<Long> getCountById(@Param("id") Long id);

    @Query("SELECT p FROM ProductEntity p WHERE p.warehouse.id = :warehouseId")
    List<ProductEntity> findByWarehouse_Id(Long warehouseId);

    @Modifying
    @Query("DELETE FROM ProductEntity p WHERE p.id IN :ids")
    @Transactional
    void deleteAllByIds(@Param("ids") Set<Long> ids);


}