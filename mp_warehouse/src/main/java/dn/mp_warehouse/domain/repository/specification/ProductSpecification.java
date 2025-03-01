package dn.mp_warehouse.domain.repository.specification;

import dn.mp_warehouse.api.dto.product.ProductFilterDto;
import dn.mp_warehouse.domain.entity.ProductEntity;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public interface ProductSpecification {

    static Specification<ProductEntity> withFilter(ProductFilterDto productFilterDto) {
        return Specification.where(isEquals(productFilterDto.getCategory()))
                .and(isEquals(productFilterDto.getMinPrice(), productFilterDto.getMaxPrice()))
                .and(isEquals("country", productFilterDto.getCountry()))
                .and(isEquals("category",productFilterDto.getCategory())
                .and(isEquals(productFilterDto.getBrand(),productFilterDto.getMinSize(),
                        productFilterDto.getMaxSize())));
    }


    private static Specification<ProductEntity> isEquals(String category) {
        return (root, query, criteriaBuilder) -> {
            if (category == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("category"), category);
        };
    }

    private static Specification<ProductEntity> isEquals(BigDecimal minPrice, BigDecimal maxPrice) {
        return (root, query, criteriaBuilder) -> {
            if (minPrice == null && maxPrice == null) {
                return null;
            }
            if (minPrice == null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("cost"), maxPrice);
            }
            if (maxPrice == null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("cost"), minPrice);
            }
            return criteriaBuilder.between(root.get("cost"), minPrice, maxPrice);
        };
    }

    private static <T>  Specification<ProductEntity> isEquals(String fieldName, T object) {
        return (root, query, criteriaBuilder) -> {
            if (fieldName == null || object == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get(fieldName),object);
        };
    }

    private static Specification<ProductEntity> isEquals(String brand,Integer minSize, Integer maxSize) {
        return (root, query, criteriaBuilder) -> {
            if (brand == null && minSize == null && maxSize == null) {
                return null;
            }
            if (minSize == null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("size"), maxSize);
            }
            if (maxSize == null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("size"), minSize);
            }
            return criteriaBuilder.between(root.get("size"), minSize, maxSize);
        };
    }
}
