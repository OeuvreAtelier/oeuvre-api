package com.muffincrunchy.oeuvreapi.controller;

import com.muffincrunchy.oeuvreapi.model.dto.request.CreateProductReviewRequest;
import com.muffincrunchy.oeuvreapi.model.dto.response.CommonResponse;
import com.muffincrunchy.oeuvreapi.model.dto.response.ProductReviewResponse;
import com.muffincrunchy.oeuvreapi.model.entity.ProductReview;
import com.muffincrunchy.oeuvreapi.service.ProductReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.muffincrunchy.oeuvreapi.model.constant.ApiUrl.PRODUCT_REVIEW_URL;

@RequiredArgsConstructor
@RestController
@RequestMapping(PRODUCT_REVIEW_URL)
public class ProductReviewController {

    private final ProductReviewService productReviewService;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    public ResponseEntity<CommonResponse<List<ProductReviewResponse>>> getAllReviews() {
        List<ProductReviewResponse> productReview = productReviewService.getAll();
        CommonResponse<List<ProductReviewResponse>> response = CommonResponse.<List<ProductReviewResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("success fetch data")
                .data(productReview)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{user_id}")
    public ResponseEntity<CommonResponse<List<ProductReviewResponse>>> getUserReviews(@PathVariable("user_id") String userId) {
        List<ProductReviewResponse> productReview = productReviewService.getByUserID(userId);
        CommonResponse<List<ProductReviewResponse>> response = CommonResponse.<List<ProductReviewResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("success fetch data")
                .data(productReview)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/product/{product_id}")
    public ResponseEntity<CommonResponse<List<ProductReviewResponse>>> getProductReviews(@PathVariable("product_id") String productId) {
        List<ProductReviewResponse> productReview = productReviewService.getByProductId(productId);
        CommonResponse<List<ProductReviewResponse>> response = CommonResponse.<List<ProductReviewResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("success fetch data")
                .data(productReview)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CommonResponse<ProductReviewResponse>> createProductReviews(@RequestBody CreateProductReviewRequest request) {
        ProductReviewResponse productReview = productReviewService.create(request);
        CommonResponse<ProductReviewResponse> response = CommonResponse.<ProductReviewResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("success save data")
                .data(productReview)
                .build();
        return ResponseEntity.ok(response);
    }
}
