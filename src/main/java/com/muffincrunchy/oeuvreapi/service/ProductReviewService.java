package com.muffincrunchy.oeuvreapi.service;

import com.muffincrunchy.oeuvreapi.model.dto.request.CreateProductReviewRequest;
import com.muffincrunchy.oeuvreapi.model.dto.response.ProductReviewResponse;
import com.muffincrunchy.oeuvreapi.model.entity.ProductReview;

import java.util.List;

public interface ProductReviewService {

    List<ProductReviewResponse> getAll();
    List<ProductReviewResponse> getByProductId(String productId);
    List<ProductReviewResponse> getByUserID(String userId);
    ProductReview getById(String id);
    ProductReviewResponse getResponseById(String id);
    ProductReviewResponse create(CreateProductReviewRequest request);
    void delete(String id);
}
