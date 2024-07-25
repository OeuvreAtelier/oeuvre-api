package com.muffincrunchy.oeuvreapi.repository;

import com.muffincrunchy.oeuvreapi.model.entity.ProductReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductReviewRepository extends JpaRepository<ProductReview, String> {
    List<ProductReview> findAllByProductId(String productId);
    List<ProductReview> findAllByUserId(String userId);
}
