package dn.mp_warehouse.domain.repository.specification;

import dn.mp_warehouse.domain.entity.ShopEntity;
import org.springframework.data.jpa.domain.Specification;

public interface ShopSpecification {

    static Specification<ShopEntity> withFilter(){
        return Specification.where(null);
    }
}
