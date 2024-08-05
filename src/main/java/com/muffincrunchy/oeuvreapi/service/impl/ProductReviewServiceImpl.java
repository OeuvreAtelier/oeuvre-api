package com.muffincrunchy.oeuvreapi.service.impl;

import com.muffincrunchy.oeuvreapi.model.dto.request.CreateProductReviewRequest;
import com.muffincrunchy.oeuvreapi.model.dto.request.PagingRequest;
import com.muffincrunchy.oeuvreapi.model.dto.response.ProductReviewResponse;
import com.muffincrunchy.oeuvreapi.model.dto.response.RatingResponse;
import com.muffincrunchy.oeuvreapi.model.entity.ProductReview;
import com.muffincrunchy.oeuvreapi.repository.ProductReviewRepository;
import com.muffincrunchy.oeuvreapi.service.ProductReviewService;
import com.muffincrunchy.oeuvreapi.service.ProductService;
import com.muffincrunchy.oeuvreapi.service.TransactionDetailService;
import com.muffincrunchy.oeuvreapi.service.UserService;
import com.muffincrunchy.oeuvreapi.utils.parsing.ToResponse;
import com.muffincrunchy.oeuvreapi.utils.validation.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.OptionalDouble;

@RequiredArgsConstructor
@Service
public class ProductReviewServiceImpl implements ProductReviewService {

    private final ProductReviewRepository productReviewRepository;
    private final ProductService productService;
    private final UserService userService;
    private final TransactionDetailService transactionDetailService;
    private final Validation validation;

    @Transactional(readOnly = true)
    @Override
    public List<ProductReviewResponse> getAll() {
        List<ProductReview> productReviews = productReviewRepository.findAll();
        return productReviews.stream().map(ToResponse::parseProductReview).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ProductReviewResponse> getByProductId(PagingRequest pagingRequest, String productId) {
        if (pagingRequest.getPage() <= 0) {
            pagingRequest.setPage(1);
        }
        Sort sort = Sort.by(Sort.Direction.fromString(pagingRequest.getDirection()), pagingRequest.getSortBy());
        Pageable pageable = PageRequest.of(pagingRequest.getPage()-1, pagingRequest.getSize(), sort);
        List<ProductReview> productReviews = productReviewRepository.findAllByProductId(productId);
        List<ProductReviewResponse> productReviewResponses = productReviews.stream().map(ToResponse::parseProductReview).toList();
        final int start = (int) pageable.getOffset();
        final int end = Math.min(start + pageable.getPageSize(), productReviewResponses.size());
        return new PageImpl<>(productReviewResponses.subList(start, end), pageable, productReviewResponses.size());
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ProductReviewResponse> getByUserID(PagingRequest pagingRequest, String userId) {
        if (pagingRequest.getPage() <= 0) {
            pagingRequest.setPage(1);
        }
        Sort sort = Sort.by(Sort.Direction.fromString(pagingRequest.getDirection()), pagingRequest.getSortBy());
        Pageable pageable = PageRequest.of(pagingRequest.getPage()-1, pagingRequest.getSize(), sort);
        List<ProductReview> productReviews = productReviewRepository.findAllByUserId(userId);
        List<ProductReviewResponse> productReviewResponses = productReviews.stream().map(ToResponse::parseProductReview).toList();
        final int start = (int) pageable.getOffset();
        final int end = Math.min(start + pageable.getPageSize(), productReviewResponses.size());
        return new PageImpl<>(productReviewResponses.subList(start, end), pageable, productReviewResponses.size());
    }

    @Transactional(readOnly = true)
    @Override
    public ProductReview getById(String id) {
        return productReviewRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public ProductReviewResponse getByTransactionDetailId(String transactionDetailId) {
        ProductReview productReview = productReviewRepository.findByTransactionDetailId(transactionDetailId).orElse(null);
        if (productReview != null) {
            return ToResponse.parseProductReview(productReview);
        }
        return null;
    }

    @Transactional(readOnly = true)
    @Override
    public ProductReviewResponse getResponseById(String id) {
        ProductReview productReview = productReviewRepository.findById(id).orElse(null);
        if (productReview != null) {
            return ToResponse.parseProductReview(productReview);
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ProductReviewResponse create(CreateProductReviewRequest request) {
        validation.validate(request);
        ProductReview productReview = ProductReview.builder()
                .transactionDetail(transactionDetailService.getById(request.getTransactionDetailId()))
                .product(productService.getById(request.getProductId()))
                .user(userService.getById(request.getUserId()))
                .rating(request.getRating())
                .review(request.getReview())
                .createdAt(new Date())
                .build();
        productReviewRepository.saveAndFlush(productReview);
        return ToResponse.parseProductReview(productReview);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(String id) {
        productReviewRepository.deleteById(id);
    }

    @Override
    public RatingResponse getAverageRating(String productId) {
        List<ProductReview> productReviews = productReviewRepository.findAllByProductId(productId);
        if (!productReviews.isEmpty()) {
            OptionalDouble avgRate = productReviews.stream().mapToDouble(ProductReview::getRating).average();
            return RatingResponse.builder()
                    .productId(productId)
                    .rating(avgRate.isPresent() ? avgRate.getAsDouble() : 0.0)
                    .build();
        }
        return RatingResponse.builder()
                .productId(productId)
                .rating(0.0)
                .build();
    }
}
