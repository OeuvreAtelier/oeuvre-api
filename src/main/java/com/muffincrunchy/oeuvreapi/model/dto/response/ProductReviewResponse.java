package com.muffincrunchy.oeuvreapi.model.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ProductReviewResponse {

    private String id;
    private UserResponse user;
    private ProductResponse product;
    private double rating;
    private String review;
    private Date createdAt;
}
