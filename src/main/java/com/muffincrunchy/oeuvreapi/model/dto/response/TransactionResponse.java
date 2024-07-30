package com.muffincrunchy.oeuvreapi.model.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class TransactionResponse {

    private String id;
    private AddressResponse address;
    private UserResponse user;
    private Date transactionDate;
    private List<TransactionDetailResponse> transactionDetails;
    private PaymentResponse payment;
}
