package com.muffincrunchy.oeuvreapi.service.impl;

import com.muffincrunchy.oeuvreapi.model.dto.response.TransactionDetailResponse;
import com.muffincrunchy.oeuvreapi.model.entity.TransactionDetail;
import com.muffincrunchy.oeuvreapi.repository.TransactionDetailRepository;
import com.muffincrunchy.oeuvreapi.service.TransactionDetailService;
import com.muffincrunchy.oeuvreapi.utils.parsing.ToResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TransactionDetailServiceImp implements TransactionDetailService {

    private final TransactionDetailRepository transactionDetailRepository;

    @Override
    public List<TransactionDetail> createBulk(List<TransactionDetail> transactionDetails) {
        return transactionDetailRepository.saveAllAndFlush(transactionDetails);
    }

    @Override
    public TransactionDetail getById(String id) {
        return transactionDetailRepository.findById(id).orElse(null);
    }

    @Override
    public TransactionDetailResponse getResponseById(String id) {
        TransactionDetail transactionDetail = transactionDetailRepository.findById(id).orElse(null);
        if (transactionDetail != null) {
            return ToResponse.parseTransactionDetail(transactionDetail);
        }
        return null;
    }
}
