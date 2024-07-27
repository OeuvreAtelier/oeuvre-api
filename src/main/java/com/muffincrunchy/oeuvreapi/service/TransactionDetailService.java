package com.muffincrunchy.oeuvreapi.service;

import com.muffincrunchy.oeuvreapi.model.dto.response.TransactionDetailResponse;
import com.muffincrunchy.oeuvreapi.model.entity.TransactionDetail;

import java.util.List;

public interface TransactionDetailService {

    List<TransactionDetail> createBulk(List<TransactionDetail> transactionDetails);
    TransactionDetail getById(String id);
    TransactionDetailResponse getResponseById(String id);
}
