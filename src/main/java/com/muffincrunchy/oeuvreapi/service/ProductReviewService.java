package com.muffincrunchy.oeuvreapi.service;

import com.muffincrunchy.oeuvreapi.model.dto.request.CreateProductReviewRequest;
import com.muffincrunchy.oeuvreapi.model.dto.request.PagingRequest;
import com.muffincrunchy.oeuvreapi.model.dto.response.ProductReviewResponse;
import com.muffincrunchy.oeuvreapi.model.entity.ProductReview;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductReviewService {

    List<ProductReviewResponse> getAll();
    Page<ProductReviewResponse> getByProductId(PagingRequest pagingRequest, String productId);
    Page<ProductReviewResponse> getByUserID(PagingRequest pagingRequest, String userId);
    ProductReview getById(String id);
    ProductReviewResponse getResponseById(String id);
    ProductReviewResponse getByTransactionDetailId(String transactionDetailId);
    ProductReviewResponse create(CreateProductReviewRequest request);
    void delete(String id);
}
