package com.inditex.price.adapter.out.persistence;

import java.time.LocalDateTime;
import org.springframework.data.jpa.domain.Specification;

public final class PriceSpecifications {

    private PriceSpecifications() {
    }

    public static Specification<PriceEntity> hasProductId(Long productId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("productId"), productId);
    }

    public static Specification<PriceEntity> hasBrandId(Integer brandId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("brandId"), brandId);
    }

    public static Specification<PriceEntity> applicationDateBetweenStartAndEnd(LocalDateTime applicationDate) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.lessThanOrEqualTo(root.get("startDate"), applicationDate),
                criteriaBuilder.greaterThanOrEqualTo(root.get("endDate"), applicationDate)
        );
    }
}

