package com.muffincrunchy.oeuvreapi.utils.specification;

import com.muffincrunchy.oeuvreapi.model.dto.request.SearchProductRequest;
import jakarta.persistence.criteria.Predicate;
import com.muffincrunchy.oeuvreapi.model.entity.Product;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ProductSpecification {
    public static Specification<Product> getSpecification(SearchProductRequest request) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (request.getName() != null) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + request.getName().toLowerCase() + "%"));
            }
            return query.where(criteriaBuilder.or(predicates.toArray(Predicate[]::new))).getRestriction();
        };
    }
}
