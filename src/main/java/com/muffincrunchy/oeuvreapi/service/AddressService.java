package com.muffincrunchy.oeuvreapi.service;

import com.muffincrunchy.oeuvreapi.model.dto.request.CreateAddressRequest;
import com.muffincrunchy.oeuvreapi.model.dto.request.UpdateAddressRequest;
import com.muffincrunchy.oeuvreapi.model.dto.response.AddressResponse;
import com.muffincrunchy.oeuvreapi.model.entity.Address;

import java.util.List;

public interface AddressService {

    List<AddressResponse> getAll();
    List<AddressResponse> getByUserId(String userId);
    AddressResponse getResponseById(String id);
    Address getById(String id);
    AddressResponse create(CreateAddressRequest request);
    AddressResponse update(UpdateAddressRequest request);
    void delete(String id);
}
