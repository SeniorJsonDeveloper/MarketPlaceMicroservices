package dn.mp_warehouse.domain.repository;

import dn.mp_warehouse.domain.entity.ShopEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShopRepository extends JpaRepository<ShopEntity,String> {

    Optional<ShopEntity> findByName(String name);
}
