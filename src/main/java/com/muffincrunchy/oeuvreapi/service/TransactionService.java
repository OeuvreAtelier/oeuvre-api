package com.muffincrunchy.oeuvreapi.service;

import com.muffincrunchy.oeuvreapi.model.dto.request.CreateTransactionRequest;
import com.muffincrunchy.oeuvreapi.model.dto.response.TransactionResponse;

import java.util.List;

public interface TransactionService {

    List<TransactionResponse> getAllTransactions();
    TransactionResponse createTransaction(CreateTransactionRequest request);
}
