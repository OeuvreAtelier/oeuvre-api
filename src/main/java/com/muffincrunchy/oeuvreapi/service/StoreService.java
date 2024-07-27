package com.muffincrunchy.oeuvreapi.service;

import com.muffincrunchy.oeuvreapi.model.dto.request.CreateStoreRequest;
import com.muffincrunchy.oeuvreapi.model.dto.request.UpdateStoreRequest;
import com.muffincrunchy.oeuvreapi.model.dto.response.StoreResponse;
import com.muffincrunchy.oeuvreapi.model.entity.Store;

import java.util.List;

public interface StoreService {

    List<StoreResponse> getAll();
    Store getById(String id);
    StoreResponse getResponseById(String id);
    StoreResponse getByUserId(String userId);
    StoreResponse create(CreateStoreRequest request);
    StoreResponse update(UpdateStoreRequest request);
    void delete(String id);
}
