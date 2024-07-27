package com.muffincrunchy.oeuvreapi.controller;

import com.muffincrunchy.oeuvreapi.model.dto.request.CreateTransactionRequest;
import com.muffincrunchy.oeuvreapi.model.dto.request.PagingRequest;
import com.muffincrunchy.oeuvreapi.model.dto.response.CommonResponse;
import com.muffincrunchy.oeuvreapi.model.dto.response.PagingResponse;
import com.muffincrunchy.oeuvreapi.model.dto.response.TransactionResponse;
import com.muffincrunchy.oeuvreapi.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.muffincrunchy.oeuvreapi.model.constant.ApiUrl.ID_PATH;
import static com.muffincrunchy.oeuvreapi.model.constant.ApiUrl.TRANSACTION_URL;

@RequiredArgsConstructor
@RestController
@RequestMapping(TRANSACTION_URL)
public class TransactionController {

    private final TransactionService transactionService;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    public ResponseEntity<CommonResponse<List<TransactionResponse>>> getAllTransaction() {
        List<TransactionResponse> transactionResponses = transactionService.getAll();
        CommonResponse<List<TransactionResponse>> response = CommonResponse.<List<TransactionResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("success fetch data")
                .data(transactionResponses)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{user_id}")
    public ResponseEntity<CommonResponse<List<TransactionResponse>>> getAllTransactionByUserId(
            @PathVariable("user_id") String userId,
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "sortBy", defaultValue = "transactionDate") String sortBy,
            @RequestParam(name = "direction", defaultValue = "desc") String direction
    ) {
        PagingRequest pagingRequest = PagingRequest.builder()
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .direction(direction)
                .build();
        Page<TransactionResponse> transactions = transactionService.getAllByUserId(pagingRequest, userId);
        PagingResponse pagingResponse = PagingResponse.builder()
                .totalPages(transactions.getTotalPages())
                .totalElements(transactions.getTotalElements())
                .page(transactions.getPageable().getPageNumber()+1)
                .size(transactions.getPageable().getPageSize())
                .hasNext(transactions.hasNext())
                .hasPrevious(transactions.hasPrevious())
                .build();
        CommonResponse<List<TransactionResponse>> response = CommonResponse.<List<TransactionResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("success fetch data")
                .data(transactions.getContent())
                .paging(pagingResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping(ID_PATH)
    public ResponseEntity<CommonResponse<TransactionResponse>> getTransactionById(@PathVariable("id") String id) {
        TransactionResponse transactionResponses = transactionService.getById(id);
        CommonResponse<TransactionResponse> response = CommonResponse.<TransactionResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("success fetch data")
                .data(transactionResponses)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CommonResponse<TransactionResponse>> createTransaction(@RequestBody CreateTransactionRequest request) {
        TransactionResponse transactionResponse = transactionService.create(request);
        CommonResponse<TransactionResponse> response = CommonResponse.<TransactionResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("success save data")
                .data(transactionResponse)
                .build();
        return ResponseEntity.ok(response);
    }
}
