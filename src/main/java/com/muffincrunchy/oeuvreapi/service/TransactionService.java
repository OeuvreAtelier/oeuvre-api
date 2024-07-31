package com.muffincrunchy.oeuvreapi.service;

import com.muffincrunchy.oeuvreapi.model.dto.request.CreateTransactionRequest;
import com.muffincrunchy.oeuvreapi.model.dto.request.PagingRequest;
import com.muffincrunchy.oeuvreapi.model.dto.request.UpdatePaymentStatusRequest;
import com.muffincrunchy.oeuvreapi.model.dto.response.TransactionResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TransactionService {

    List<TransactionResponse> getAll();
    Page<TransactionResponse> getAllByUserId(PagingRequest pagingRequest, String userId);
    TransactionResponse getById(String id);
    TransactionResponse create(CreateTransactionRequest request);
    void updateStatus(UpdatePaymentStatusRequest request);
}
