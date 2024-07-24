package com.muffincrunchy.oeuvreapi.service.impl;

import com.muffincrunchy.oeuvreapi.model.dto.request.CreateTransactionRequest;
import com.muffincrunchy.oeuvreapi.model.dto.response.TransactionDetailResponse;
import com.muffincrunchy.oeuvreapi.model.dto.response.TransactionResponse;
import com.muffincrunchy.oeuvreapi.model.entity.*;
import com.muffincrunchy.oeuvreapi.repository.TransactionRepository;
import com.muffincrunchy.oeuvreapi.service.*;
import com.muffincrunchy.oeuvreapi.utils.validation.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionDetailService transactionDetailService;
    private final UserService userService;
    private final ProductService productService;
    private final Validation validation;

    @Override
    public List<TransactionResponse> getAllTransactions() {
        List<Transaction> transactions = transactionRepository.getAllTransactions();
        return transactions.stream().map(
                transaction -> {
                    List<TransactionDetailResponse> transactionDetailResponses = transaction.getTransactionDetails().stream().map(
                            transactionDetail -> TransactionDetailResponse.builder()
                                    .Id(transactionDetail.getId())
                                    .merchId(transactionDetail.getProduct().getId())
                                    .merchPrice(transactionDetail.getMerchPrice())
                                    .qty(transactionDetail.getQty())
                                    .build()
                    ).toList();
                    return TransactionResponse.builder()
                            .id(transaction.getId())
                            .userId(transaction.getUser().getId())
                            .transDate(transaction.getTransDate())
                            .transactionDetails(transactionDetailResponses)
                            .build();
                }
        ).toList();
    }

    @Override
    public TransactionResponse createTransaction(CreateTransactionRequest request) {
        validation.validate(request);
        User user = userService.getById(request.getUserId());
        Transaction transaction = Transaction.builder()
                .id(UUID.randomUUID().toString())
                .user(user)
                .transDate(new Date())
                .build();
        transactionRepository.createTransaction(
                transaction.getId(),
                transaction.getUser().getId(),
                transaction.getTransDate()
        );

        List<TransactionDetail> transactionDetails = request.getTransactionDetails().stream().map(
                transactionDetail -> {
                    Product product = productService.getById(transactionDetail.getMerchId());
                    if (product.getStock() < transactionDetail.getQty()) {
                        throw  new RuntimeException("Out of stock");
                    }
                    product.setStock(product.getStock()-transactionDetail.getQty());
                    return TransactionDetail.builder()
                            .id(UUID.randomUUID().toString())
                            .transaction(transaction)
                            .product(product)
                            .merchPrice(product.getPrice())
                            .qty(transactionDetail.getQty())
                            .build();
                }
        ).toList();
        transactionDetailService.createBulk(transactionDetails);
        transaction.setTransactionDetails(transactionDetails);

        List<TransactionDetailResponse> transactionDetailResponses = transactionDetails.stream().map(
                transactionDetail -> TransactionDetailResponse.builder()
                        .Id(transactionDetail.getId())
                        .merchId(transactionDetail.getProduct().getId())
                        .merchPrice(transactionDetail.getMerchPrice())
                        .qty(transactionDetail.getQty())
                        .build()
        ).toList();
        return TransactionResponse.builder()
                .id(transaction.getId())
                .userId(transaction.getUser().getId())
                .transDate(transaction.getTransDate())
                .transactionDetails(transactionDetailResponses)
                .build();
    }
}