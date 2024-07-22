package com.muffincrunchy.oeuvreapi.controller;

import com.muffincrunchy.oeuvreapi.model.dto.request.CreateTransactionRequest;
import com.muffincrunchy.oeuvreapi.model.dto.response.CommonResponse;
import com.muffincrunchy.oeuvreapi.model.dto.response.TransactionResponse;
import com.muffincrunchy.oeuvreapi.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.muffincrunchy.oeuvreapi.model.constant.ApiUrl.TRANSACTION_URL;

@RequiredArgsConstructor
@RestController
@RequestMapping(TRANSACTION_URL)
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping
    public ResponseEntity<CommonResponse<List<TransactionResponse>>> getTransaction() {
        List<TransactionResponse> transactionResponses = transactionService.getAllTransactions();
        CommonResponse<List<TransactionResponse>> response = CommonResponse.<List<TransactionResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("success fetch data")
                .data(transactionResponses)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CommonResponse<TransactionResponse>> createTransaction(@RequestBody CreateTransactionRequest request) {
        TransactionResponse transactionResponse = transactionService.createTransaction(request);
        CommonResponse<TransactionResponse> response = CommonResponse.<TransactionResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("success save data")
                .data(transactionResponse)
                .build();
        return ResponseEntity.ok(response);
    }
}
