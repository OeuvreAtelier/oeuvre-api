package com.muffincrunchy.oeuvreapi.service;

import com.muffincrunchy.oeuvreapi.model.dto.request.CreateCustomerRequest;
import com.muffincrunchy.oeuvreapi.model.dto.request.UpdateCustomerRequest;
import com.muffincrunchy.oeuvreapi.model.entity.Customer;

import java.util.List;

public interface CustomerService {

    List<Customer> getAll();
    Customer getById(String id);
    Customer create(CreateCustomerRequest request);
    Customer update(UpdateCustomerRequest request);
    void delete(String id);
}
