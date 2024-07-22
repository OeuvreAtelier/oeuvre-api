package com.muffincrunchy.oeuvreapi.service;

import com.muffincrunchy.oeuvreapi.model.dto.request.CreateMerchRequest;
import com.muffincrunchy.oeuvreapi.model.dto.request.UpdateMerchRequest;
import com.muffincrunchy.oeuvreapi.model.entity.Merch;

import java.util.List;

public interface MerchService {

    List<Merch> getAll();
    Merch getById(String id);
    Merch create(CreateMerchRequest request);
    Merch update(UpdateMerchRequest request);
    void delete(String id);
}
