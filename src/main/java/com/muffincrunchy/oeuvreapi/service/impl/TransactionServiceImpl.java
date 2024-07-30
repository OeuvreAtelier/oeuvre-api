package com.muffincrunchy.oeuvreapi.service.impl;

import com.muffincrunchy.oeuvreapi.model.dto.request.CreateTransactionRequest;
import com.muffincrunchy.oeuvreapi.model.dto.request.PagingRequest;
import com.muffincrunchy.oeuvreapi.model.dto.request.UpdatePaymentStatusRequest;
import com.muffincrunchy.oeuvreapi.model.dto.response.TransactionDetailResponse;
import com.muffincrunchy.oeuvreapi.model.dto.response.TransactionResponse;
import com.muffincrunchy.oeuvreapi.model.entity.*;
import com.muffincrunchy.oeuvreapi.repository.TransactionRepository;
import com.muffincrunchy.oeuvreapi.service.*;
import com.muffincrunchy.oeuvreapi.utils.generator.Generator;
import com.muffincrunchy.oeuvreapi.utils.parsing.ToResponse;
import com.muffincrunchy.oeuvreapi.utils.validation.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionDetailService transactionDetailService;
    private final UserService userService;
    private final ProductService productService;
    private final AddressService addressService;
    private final PaymentService paymentService;
    private final Validation validation;

    @Override
    public List<TransactionResponse> getAll() {
        List<Transaction> transactions = transactionRepository.findAll();
        return transactions.stream().map(ToResponse::parseTransaction).toList();
    }

    @Override
    public TransactionResponse getById(String id) {
        Transaction transaction = transactionRepository.findById(id).orElse(null);
        if (transaction != null) {
            return ToResponse.parseTransaction(transaction);
        }
        return null;
    }

    @Override
    public Page<TransactionResponse> getAllByUserId(PagingRequest pagingRequest, String userId) {
        transactionRepository.findAllByUserId(userId).forEach(transaction -> updateStatus(transaction.getId()));
        if (pagingRequest.getPage() <= 0) {
            pagingRequest.setPage(1);
        }
        Sort sort = Sort.by(Sort.Direction.fromString(pagingRequest.getDirection()), pagingRequest.getSortBy());
        Pageable pageable = PageRequest.of(pagingRequest.getPage()-1, pagingRequest.getSize(), sort);
        List<Transaction> transactions = transactionRepository.findAllByUserId(userId);
        List<TransactionResponse> transactionResponses = transactions.stream().map(ToResponse::parseTransaction).toList();
        final int start = (int) pageable.getOffset();
        final int end = Math.min(start + pageable.getPageSize(), transactionResponses.size());
        return new PageImpl<>(transactionResponses.subList(start, end), pageable, transactionResponses.size());
    }

    @Override
    public TransactionResponse create(CreateTransactionRequest request) {
        validation.validate(request);
        Transaction transaction = Transaction.builder()
                .user(userService.getById(request.getUserId()))
                .address(addressService.getById(request.getAddressId()))
                .transactionDate(new Date())
                .build();
        transactionRepository.saveAndFlush(transaction);

        List<TransactionDetail> transactionDetails = request.getTransactionDetails().stream().map(
                detailRequest -> {
                    Product product = productService.getById(detailRequest.getProductId());
                    if (product.getStock() < detailRequest.getQuantity()) {
                        throw new RuntimeException("Out of stock");
                    }
                    product.setStock(product.getStock() - detailRequest.getQuantity());
                    return TransactionDetail.builder()
                            .transaction(transaction)
                            .invoice(Generator.InvoiceGenerator())
                            .product(product)
                            .quantity(detailRequest.getQuantity())
                            .build();
                }
        ).toList();
        transactionDetailService.createBulk(transactionDetails);
        transaction.setTransactionDetails(transactionDetails);

        Payment payment = paymentService.createPayment(transaction);
        transaction.setPayment(payment);
        transactionRepository.updatePayment(transaction.getId(), payment.getId());

        return ToResponse.parseTransaction(transaction);
    }

    @Override
    public void updateStatus(String id) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "data not found"));
        paymentService.updateTransactionStatus(transaction.getPayment().getId());
    }
}