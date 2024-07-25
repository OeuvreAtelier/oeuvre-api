package com.muffincrunchy.oeuvreapi.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateProductReviewRequest {

    @NotBlank(message = "user_id is required")
    @JsonProperty("user_id")
    private String userId;

    @NotBlank(message = "product_id is required")
    @JsonProperty("product_id")
    private String productId;

    @NotNull(message = "rating is required")
    @Min(1)
    @Max(5)
    private double rating;

    @NotBlank(message = "review")
    private String review;
}
