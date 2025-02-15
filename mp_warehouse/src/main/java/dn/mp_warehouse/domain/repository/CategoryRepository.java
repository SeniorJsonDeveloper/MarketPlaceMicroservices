package dn.mp_warehouse.domain.repository;

import dn.mp_warehouse.domain.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
}