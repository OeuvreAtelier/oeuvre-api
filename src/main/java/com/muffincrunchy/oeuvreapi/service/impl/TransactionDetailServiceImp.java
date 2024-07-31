package com.muffincrunchy.oeuvreapi.service.impl;

import com.muffincrunchy.oeuvreapi.model.dto.response.TransactionDetailResponse;
import com.muffincrunchy.oeuvreapi.model.entity.TransactionDetail;
import com.muffincrunchy.oeuvreapi.repository.TransactionDetailRepository;
import com.muffincrunchy.oeuvreapi.service.TransactionDetailService;
import com.muffincrunchy.oeuvreapi.utils.parsing.ToResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TransactionDetailServiceImp implements TransactionDetailService {

    private final TransactionDetailRepository transactionDetailRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<TransactionDetail> createBulk(List<TransactionDetail> transactionDetails) {
        return transactionDetailRepository.saveAllAndFlush(transactionDetails);
    }

    @Transactional(readOnly = true)
    @Override
    public TransactionDetail getById(String id) {
        return transactionDetailRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public TransactionDetailResponse getResponseById(String id) {
        TransactionDetail transactionDetail = transactionDetailRepository.findById(id).orElse(null);
        if (transactionDetail != null) {
            return ToResponse.parseTransactionDetail(transactionDetail);
        }
        return null;
    }
}
