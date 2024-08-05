package com.muffincrunchy.oeuvreapi.controller;

import com.muffincrunchy.oeuvreapi.model.dto.request.CreateProductReviewRequest;
import com.muffincrunchy.oeuvreapi.model.dto.request.PagingRequest;
import com.muffincrunchy.oeuvreapi.model.dto.response.CommonResponse;
import com.muffincrunchy.oeuvreapi.model.dto.response.PagingResponse;
import com.muffincrunchy.oeuvreapi.model.dto.response.ProductReviewResponse;
import com.muffincrunchy.oeuvreapi.model.dto.response.RatingResponse;
import com.muffincrunchy.oeuvreapi.service.ProductReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.muffincrunchy.oeuvreapi.model.constant.ApiUrl.ID_PATH;
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
    public ResponseEntity<CommonResponse<List<ProductReviewResponse>>> getUserReviews(
            @PathVariable("user_id") String userId,
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "5") Integer size,
            @RequestParam(name = "sortBy", defaultValue = "createdAt") String sortBy,
            @RequestParam(name = "direction", defaultValue = "desc") String direction
    ) {
        PagingRequest pagingRequest = PagingRequest.builder()
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .direction(direction)
                .build();
        Page<ProductReviewResponse> productReview = productReviewService.getByUserID(pagingRequest, userId);
        PagingResponse pagingResponse = PagingResponse.builder()
                .totalPages(productReview.getTotalPages())
                .totalElements(productReview.getTotalElements())
                .page(productReview.getPageable().getPageNumber()+1)
                .size(productReview.getPageable().getPageSize())
                .hasNext(productReview.hasNext())
                .hasPrevious(productReview.hasPrevious())
                .build();
        CommonResponse<List<ProductReviewResponse>> response = CommonResponse.<List<ProductReviewResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("success fetch data")
                .data(productReview.getContent())
                .paging(pagingResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/product/{product_id}")
    public ResponseEntity<CommonResponse<List<ProductReviewResponse>>> getProductReviews(
            @PathVariable("product_id") String productId,
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "5") Integer size,
            @RequestParam(name = "sortBy", defaultValue = "createdAt") String sortBy,
            @RequestParam(name = "direction", defaultValue = "desc") String direction
    ) {
        PagingRequest pagingRequest = PagingRequest.builder()
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .direction(direction)
                .build();
        Page<ProductReviewResponse> productReview = productReviewService.getByProductId(pagingRequest, productId);
        PagingResponse pagingResponse = PagingResponse.builder()
                .totalPages(productReview.getTotalPages())
                .totalElements(productReview.getTotalElements())
                .page(productReview.getPageable().getPageNumber()+1)
                .size(productReview.getPageable().getPageSize())
                .hasNext(productReview.hasNext())
                .hasPrevious(productReview.hasPrevious())
                .build();
        CommonResponse<List<ProductReviewResponse>> response = CommonResponse.<List<ProductReviewResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("success fetch data")
                .data(productReview.getContent())
                .paging(pagingResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping(ID_PATH)
    public ResponseEntity<CommonResponse<ProductReviewResponse>> getProductReviewsById(@PathVariable("id") String id) {
        ProductReviewResponse productReview = productReviewService.getResponseById(id);
        CommonResponse<ProductReviewResponse> response = CommonResponse.<ProductReviewResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("success save data")
                .data(productReview)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("transaction/{transaction_id}")
    public ResponseEntity<CommonResponse<ProductReviewResponse>> getProductReviewsByTransactionDetailId(@PathVariable("transaction_id") String transactionId) {
        ProductReviewResponse productReview = productReviewService.getByTransactionDetailId(transactionId);
        CommonResponse<ProductReviewResponse> response = CommonResponse.<ProductReviewResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("success save data")
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

    @GetMapping("rating/product/{product_id}")
    public ResponseEntity<CommonResponse<RatingResponse>> getProductRating(@PathVariable("product_id") String productId) {
        RatingResponse rating = productReviewService.getAverageRating(productId);
        CommonResponse<RatingResponse> response = CommonResponse.<RatingResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("success save data")
                .data(rating)
                .build();
        return ResponseEntity.ok(response);
    }
}
