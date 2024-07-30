package com.muffincrunchy.oeuvreapi.model.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentResponse {

    private String id;
    private String token;
    private String redirectUrl;
    private String transactionStatus;
}
