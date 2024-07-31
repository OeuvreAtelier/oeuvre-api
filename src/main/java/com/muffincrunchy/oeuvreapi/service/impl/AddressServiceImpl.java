package com.muffincrunchy.oeuvreapi.service.impl;

import com.muffincrunchy.oeuvreapi.model.dto.request.CreateAddressRequest;
import com.muffincrunchy.oeuvreapi.model.dto.request.UpdateAddressRequest;
import com.muffincrunchy.oeuvreapi.model.dto.response.AddressResponse;
import com.muffincrunchy.oeuvreapi.model.entity.Address;
import com.muffincrunchy.oeuvreapi.repository.AddressRepository;
import com.muffincrunchy.oeuvreapi.service.AddressService;
import com.muffincrunchy.oeuvreapi.service.UserService;
import com.muffincrunchy.oeuvreapi.utils.parsing.ToResponse;
import com.muffincrunchy.oeuvreapi.utils.validation.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final UserService userService;
    private final Validation validation;

    @Transactional(readOnly = true)
    @Override
    public List<AddressResponse> getAll() {
        List<Address> addresses = addressRepository.findAll();
        return addresses.stream().map(ToResponse::parseAddress).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<AddressResponse> getByUserId(String userId) {
        List<Address> addresses = addressRepository.findAllByUserId(userId);
        return addresses.stream().map(ToResponse::parseAddress).toList();
    }

    @Override
    public AddressResponse getResponseById(String id) {
        Address address = addressRepository.findById(id).orElse(null);
        if (address != null) {
            return ToResponse.parseAddress(address);
        }
        return null;
    }

    @Transactional(readOnly = true)
    @Override
    public Address getById(String id) {
        return addressRepository.findById(id).orElse(null);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public AddressResponse create(CreateAddressRequest request) {
        validation.validate(request);
        Address address = Address.builder()
                .user(userService.getById(request.getUserId()))
                .country(request.getCountry())
                .state(request.getState())
                .city(request.getCity())
                .detail(request.getDetail())
                .postalCode(request.getPostalCode())
                .phoneNumber(request.getPhoneNumber())
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();
        addressRepository.saveAndFlush(address);
        return ToResponse.parseAddress(address);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public AddressResponse update(UpdateAddressRequest request) {
        validation.validate(request);
        Address address = getById(request.getId());
        address.setCountry(request.getCountry());
        address.setState(request.getState());
        address.setCity(request.getCity());
        address.setDetail(request.getDetail());
        address.setPostalCode(request.getPostalCode());
        address.setPhoneNumber(request.getPhoneNumber());
        address.setUpdatedAt(new Date());
        return ToResponse.parseAddress(address);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(String id) {
        addressRepository.deleteById(id);
    }


}
