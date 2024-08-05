package com.muffincrunchy.oeuvreapi.model.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RatingResponse {

    private String productId;
    private Double rating;
}
