package com.muffincrunchy.oeuvreapi.service.impl;

import com.muffincrunchy.oeuvreapi.model.dto.request.CreateCustomerRequest;
import com.muffincrunchy.oeuvreapi.model.dto.request.UpdateCustomerRequest;
import com.muffincrunchy.oeuvreapi.model.entity.Customer;
import com.muffincrunchy.oeuvreapi.repository.CustomerRepository;
import com.muffincrunchy.oeuvreapi.service.CustomerService;
import com.muffincrunchy.oeuvreapi.utils.Validation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final Validation validation;

    @Override
    public List<Customer> getAll() {
        return customerRepository.getAllCustomers();
    }

    @Override
    public Customer getById(String id) {
        Optional<Customer> customer = customerRepository.getCustomerById(id);
        return customer.orElse(null);
    }

    @Override
    public Customer create(CreateCustomerRequest request) {
        validation.validate(request);
        Customer customer = Customer.builder()
                .id(UUID.randomUUID().toString())
                .name(request.getName())
                .birthDate(Date.valueOf(request.getBirthDate()))
                .phoneNo(request.getPhoneNo())
                .email(request.getEmail())
                .build();
        int res = customerRepository.createCustomer(
                customer.getId(),
                customer.getName(),
                customer.getBirthDate(),
                customer.getPhoneNo(),
                customer.getEmail()
        );
        if (res == 1) {
            return customer;
        }
        return null;
    }

    @Override
    public Customer update(UpdateCustomerRequest request) {
        validation.validate(request);
        int res = customerRepository.updateCustomerById(
                request.getId(),
                request.getName(),
                Date.valueOf(request.getBirthDate()),
                request.getPhoneNo(),
                request.getEmail()
        );
        if (res == 1) {
            return getById(request.getId());
        }
        return null;
    }

    @Override
    public void delete(String id) {
        customerRepository.deleteCustomerById(id);
    }
}
