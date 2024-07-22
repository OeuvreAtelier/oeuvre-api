package com.muffincrunchy.oeuvreapi.model.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionDetailResponse {

    private String Id;
    private String merchId;
    private String shipmentId;
    private Long merchPrice;
    private Long shipmentFee;
    private Integer qty;
    private String payment;
}
