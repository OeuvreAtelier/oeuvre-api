package com.muffincrunchy.oeuvreapi.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.muffincrunchy.oeuvreapi.model.dto.request.PaymentDetailRequest;
import com.muffincrunchy.oeuvreapi.model.dto.request.PaymentProductDetailRequest;
import com.muffincrunchy.oeuvreapi.model.dto.request.PaymentRequest;
import com.muffincrunchy.oeuvreapi.model.dto.request.PaymentUserRequest;
import com.muffincrunchy.oeuvreapi.model.entity.Payment;
import com.muffincrunchy.oeuvreapi.model.entity.Transaction;
import com.muffincrunchy.oeuvreapi.repository.PaymentRepository;
import com.muffincrunchy.oeuvreapi.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class PaymentSeviceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final RestClient restClient;
    private final String MERCHANT_KEY;
    private final String MERCHANT_URL;
    private final ObjectMapper objectMapper;

    @Autowired
    public PaymentSeviceImpl(PaymentRepository paymentRepository, RestClient restClient, @Value("${oeuvre.midtrans.merchant-key}") String MERCHANT_KEY, @Value("${oeuvre.midtrans.endpoint}") String MERCHANT_URL, ObjectMapper objectMapper) {
        this.paymentRepository = paymentRepository;
        this.restClient = restClient;
        this.MERCHANT_KEY = MERCHANT_KEY;
        this.MERCHANT_URL = MERCHANT_URL;
        this.objectMapper = objectMapper;
    }

    @Override
    public Payment createPayment(Transaction transaction) {
        long amount = transaction.getTransactionDetails().stream().mapToLong(
                transactionDetail -> (transactionDetail.getQuantity() * transactionDetail.getProduct().getPrice())
        ).reduce(0, Long::sum);

        PaymentDetailRequest paymentDetailRequest = PaymentDetailRequest.builder()
                .orderId(transaction.getId())
                .grossAmount(amount)
                .build();

        List<PaymentProductDetailRequest> paymentProductDetailRequests = transaction.getTransactionDetails().stream().map(
                transactionDetail -> PaymentProductDetailRequest.builder()
                        .name(transactionDetail.getProduct().getName())
                        .price(transactionDetail.getProduct().getPrice())
                        .quantity(transactionDetail.getQuantity())
                        .build()
        ).toList();

        PaymentUserRequest paymentUserRequest = PaymentUserRequest.builder()
                .firstName(transaction.getUser().getFirstName())
                .lastName(transaction.getUser().getLastName())
                .build();

        PaymentRequest request = PaymentRequest.builder()
                .paymentDetail(paymentDetailRequest)
                .paymentProductDetails(paymentProductDetailRequests)
                .paymentMethods(List.of("gopay", "shopeepay", "other_qris"))
                .user(paymentUserRequest)
                .build();

        ResponseEntity<Map<String, String>> midtransResponse = restClient.post()
                .uri(MERCHANT_URL)
                .body(request)
                .header(HttpHeaders.AUTHORIZATION, "Basic " + MERCHANT_KEY)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<>() {
                });
        Map<String, String> body = midtransResponse.getBody();
        if (body == null) {
            return null;
        }
        Payment payment = Payment.builder()
                .token(body.get("token"))
                .redirectUrl(body.get("redirect_url"))
                .transactionStatus("ordered")
                .build();
        return paymentRepository.saveAndFlush(payment);
    }
}
