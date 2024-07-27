package com.muffincrunchy.oeuvreapi.model.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionDetailResponse {

    private String id;
    private String invoice;
    private ProductResponse product;
    private Integer quantity;
}
