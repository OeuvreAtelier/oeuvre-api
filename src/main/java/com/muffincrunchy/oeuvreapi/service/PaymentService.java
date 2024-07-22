package com.muffincrunchy.oeuvreapi.service;

import com.muffincrunchy.oeuvreapi.model.constant.PaymentType;
import com.muffincrunchy.oeuvreapi.model.entity.Payment;

import java.util.List;

public interface PaymentService {

    Payment getOrSave(PaymentType paymentType);
    List<Payment> getAll();
}
