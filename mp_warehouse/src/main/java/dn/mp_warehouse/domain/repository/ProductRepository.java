package dn.mp_warehouse.domain.repository;

import dn.mp_warehouse.domain.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long>, JpaSpecificationExecutor<ProductEntity> {


    Optional<ProductEntity> findByProductName(String name);


    @Query("SELECT p.price FROM ProductEntity p WHERE p.id = :id")
    Optional<BigDecimal> getPriceById(@Param("id") Long id);

    @Query("SELECT p.countOfProducts FROM ProductEntity p where p.id = :id")
    Optional<Long> getCountById(@Param("id") Long id);

}