package com.muffincrunchy.oeuvreapi.model.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class TransactionResponse {

    private String id;
    private String customerId;
    private Date transDate;
    private List<TransactionDetailResponse> transactionDetails;
}
