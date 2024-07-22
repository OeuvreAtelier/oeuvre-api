package com.muffincrunchy.oeuvreapi.service.impl;

import com.muffincrunchy.oeuvreapi.model.constant.PaymentType;
import com.muffincrunchy.oeuvreapi.model.entity.Payment;
import com.muffincrunchy.oeuvreapi.repository.PaymentRepository;
import com.muffincrunchy.oeuvreapi.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Override
    public Payment getOrSave(PaymentType paymentType) {
        return paymentRepository.findByPayment(paymentType).orElseGet(() -> paymentRepository.saveAndFlush(Payment.builder().payment(paymentType).build()));
    }

    @Override
    public List<Payment> getAll() {
        return paymentRepository.findAll();
    }
}
