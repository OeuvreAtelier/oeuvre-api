package com.muffincrunchy.oeuvreapi.utils.specification;

import com.muffincrunchy.oeuvreapi.model.constant.ItemCategory;
import com.muffincrunchy.oeuvreapi.model.constant.ItemType;
import com.muffincrunchy.oeuvreapi.model.dto.request.SearchProductRequest;
import jakarta.persistence.criteria.Predicate;
import com.muffincrunchy.oeuvreapi.model.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ProductSpecification {
    public static Specification<Product> getSpecification(SearchProductRequest request) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (request.getName() != null) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + request.getName().toLowerCase() + "%"));
            }
            if (request.getCategory() != null) {
                predicates.add(criteriaBuilder.equal(root.get("category").get("category"), ItemCategory.valueOf(request.getCategory())));
            }
            if (request.getType() != null) {
                predicates.add(criteriaBuilder.equal(root.get("type").get("type"), ItemType.valueOf(request.getType())));
            }
//            log.info(String.valueOf(root.get("category").get("category")));
            return query.where(criteriaBuilder.and(predicates.toArray(Predicate[]::new))).getRestriction();
        };
    }
}
