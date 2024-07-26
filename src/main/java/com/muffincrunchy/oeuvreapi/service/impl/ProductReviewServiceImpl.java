package com.muffincrunchy.oeuvreapi.service.impl;

import com.muffincrunchy.oeuvreapi.model.dto.request.CreateProductReviewRequest;
import com.muffincrunchy.oeuvreapi.model.dto.response.ProductReviewResponse;
import com.muffincrunchy.oeuvreapi.model.entity.ProductReview;
import com.muffincrunchy.oeuvreapi.repository.ProductReviewRepository;
import com.muffincrunchy.oeuvreapi.service.ProductReviewService;
import com.muffincrunchy.oeuvreapi.service.ProductService;
import com.muffincrunchy.oeuvreapi.service.UserService;
import com.muffincrunchy.oeuvreapi.utils.parsing.ToResponse;
import com.muffincrunchy.oeuvreapi.utils.validation.Validation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductReviewServiceImpl implements ProductReviewService {

    private final ProductReviewRepository productReviewRepository;
    private final ProductService productService;
    private final UserService userService;
    private final Validation validation;

    @Override
    public List<ProductReviewResponse> getAll() {
        List<ProductReview> productReviews = productReviewRepository.findAll();
        return productReviews.stream().map(ToResponse::parseProductReview).toList();
    }

    @Override
    public List<ProductReviewResponse> getByProductId(String productId) {
        List<ProductReview> productReviews = productReviewRepository.findAllByProductId(productId);
        return productReviews.stream().map(ToResponse::parseProductReview).toList();
    }

    @Override
    public List<ProductReviewResponse> getByUserID(String userId) {
        List<ProductReview> productReviews = productReviewRepository.findAllByUserId(userId);
        return productReviews.stream().map(ToResponse::parseProductReview).toList();
    }

    @Override
    public ProductReview getById(String id) {
        return productReviewRepository.findById(id).orElse(null);
    }

    @Override
    public ProductReviewResponse getResponseById(String id) {
        ProductReview productReview = productReviewRepository.findById(id).orElse(null);
        if (productReview != null) {
            return ToResponse.parseProductReview(productReview);
        }
        return null;
    }

    @Override
    public ProductReviewResponse create(CreateProductReviewRequest request) {
        validation.validate(request);
        ProductReview productReview = ProductReview.builder()
                .product(productService.getById(request.getProductId()))
                .user(userService.getById(request.getUserId()))
                .rating(request.getRating())
                .review(request.getReview())
                .createdAt(new Date())
                .build();
        productReviewRepository.saveAndFlush(productReview);
        return ToResponse.parseProductReview(productReview);
    }

    @Override
    public void delete(String id) {
        productReviewRepository.deleteById(id);
    }
}
