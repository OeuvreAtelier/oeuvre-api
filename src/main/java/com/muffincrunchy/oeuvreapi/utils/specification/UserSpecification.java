package com.muffincrunchy.oeuvreapi.utils.specification;

import com.muffincrunchy.oeuvreapi.model.dto.request.SearchArtistRequest;
import com.muffincrunchy.oeuvreapi.model.entity.User;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class UserSpecification {
    public static Specification<User> getSpecification(SearchArtistRequest request) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (request.getDisplayName() != null) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("displayName")), "%" + request.getDisplayName().toLowerCase() + "%"));
            }
            predicates.add(criteriaBuilder.equal(root.get("isArtist"), true));
            return query.where(criteriaBuilder.and(predicates.toArray(Predicate[]::new))).getRestriction();
        };
    }
}
