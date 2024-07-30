package com.muffincrunchy.oeuvreapi.service;

import com.muffincrunchy.oeuvreapi.model.entity.Payment;
import com.muffincrunchy.oeuvreapi.model.entity.Transaction;

public interface PaymentService {

    Payment createPayment(Transaction transaction);
    void updateTransactionStatus(String id);
}
