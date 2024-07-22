package com.muffincrunchy.oeuvreapi.service.impl;

import com.muffincrunchy.oeuvreapi.model.constant.PaymentType;
import com.muffincrunchy.oeuvreapi.model.dto.request.CreateTransactionRequest;
import com.muffincrunchy.oeuvreapi.model.dto.response.TransactionDetailResponse;
import com.muffincrunchy.oeuvreapi.model.dto.response.TransactionResponse;
import com.muffincrunchy.oeuvreapi.model.entity.*;
import com.muffincrunchy.oeuvreapi.repository.TransactionRepository;
import com.muffincrunchy.oeuvreapi.service.*;
import com.muffincrunchy.oeuvreapi.utils.Validation;
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
    private final CustomerService customerService;
    private final MerchService merchService;
    private final ShipmentService shipmentService;
    private final PaymentService paymentService;
    private final Validation validation;

    @Override
    public List<TransactionResponse> getAllTransactions() {
        List<Transaction> transactions = transactionRepository.getAllTransactions();
        return transactions.stream().map(
                transaction -> {
                    List<TransactionDetailResponse> transactionDetailResponses = transaction.getTransactionDetails().stream().map(
                            transactionDetail -> TransactionDetailResponse.builder()
                                    .Id(transactionDetail.getId())
                                    .merchId(transactionDetail.getMerch().getId())
                                    .shipmentId(transactionDetail.getShipment().getId())
                                    .merchPrice(transactionDetail.getMerchPrice())
                                    .shipmentFee(transactionDetail.getShipmentFee())
                                    .qty(transactionDetail.getQty())
                                    .payment(transactionDetail.getPayment().getPayment().toString())
                                    .build()
                    ).toList();
                    return TransactionResponse.builder()
                            .id(transaction.getId())
                            .customerId(transaction.getCustomer().getId())
                            .transDate(transaction.getTransDate())
                            .transactionDetails(transactionDetailResponses)
                            .build();
                }
        ).toList();
    }

    @Override
    public TransactionResponse createTransaction(CreateTransactionRequest request) {
        validation.validate(request);
        Customer customer = customerService.getById(request.getCustomerId());
        Transaction transaction = Transaction.builder()
                .id(UUID.randomUUID().toString())
                .customer(customer)
                .transDate(new Date())
                .build();
        transactionRepository.createTransaction(
                transaction.getId(),
                transaction.getCustomer().getId(),
                transaction.getTransDate()
        );

        List<TransactionDetail> transactionDetails = request.getTransactionDetails().stream().map(
                transactionDetail -> {
                    Merch merch = merchService.getById(transactionDetail.getMerchId());
                    Shipment shipment = shipmentService.getById(transactionDetail.getShipmentId());
                    Payment payment = paymentService.getOrSave(PaymentType.valueOf(transactionDetail.getPayment()));
                    if (merch.getStock() < transactionDetail.getQty()) {
                        throw  new RuntimeException("Out of stock");
                    }
                    merch.setStock(merch.getStock()-transactionDetail.getQty());
                    return TransactionDetail.builder()
                            .id(UUID.randomUUID().toString())
                            .transaction(transaction)
                            .merch(merch)
                            .shipment(shipment)
                            .merchPrice(merch.getPrice())
                            .shipmentFee(shipment.getFee())
                            .qty(transactionDetail.getQty())
                            .payment(payment)
                            .build();
                }
        ).toList();
        transactionDetailService.createBulk(transactionDetails);
        transaction.setTransactionDetails(transactionDetails);

        List<TransactionDetailResponse> transactionDetailResponses = transactionDetails.stream().map(
                transactionDetail -> TransactionDetailResponse.builder()
                        .Id(transactionDetail.getId())
                        .merchId(transactionDetail.getMerch().getId())
                        .shipmentId(transactionDetail.getShipment().getId())
                        .merchPrice(transactionDetail.getMerchPrice())
                        .shipmentFee(transactionDetail.getShipmentFee())
                        .qty(transactionDetail.getQty())
                        .payment(transactionDetail.getPayment().getPayment().toString())
                        .build()
        ).toList();
        return TransactionResponse.builder()
                .id(transaction.getId())
                .customerId(transaction.getCustomer().getId())
                .transDate(transaction.getTransDate())
                .transactionDetails(transactionDetailResponses)
                .build();
    }
}
