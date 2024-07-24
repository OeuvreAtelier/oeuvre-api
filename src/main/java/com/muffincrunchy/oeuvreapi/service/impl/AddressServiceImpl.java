package com.muffincrunchy.oeuvreapi.service.impl;

import com.muffincrunchy.oeuvreapi.model.dto.request.CreateAddressRequest;
import com.muffincrunchy.oeuvreapi.model.dto.request.UpdateAddressRequest;
import com.muffincrunchy.oeuvreapi.model.dto.response.AddressResponse;
import com.muffincrunchy.oeuvreapi.model.dto.response.UserResponse;
import com.muffincrunchy.oeuvreapi.model.entity.Address;
import com.muffincrunchy.oeuvreapi.model.entity.User;
import com.muffincrunchy.oeuvreapi.repository.AddressRepository;
import com.muffincrunchy.oeuvreapi.service.AddressService;
import com.muffincrunchy.oeuvreapi.service.UserService;
import com.muffincrunchy.oeuvreapi.utils.validation.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final UserService userService;
    private final Validation validation;

    @Override
    public List<AddressResponse> getAll() {
        List<Address> addresses = addressRepository.findAll();
        return addresses.stream().map(this::parseAddressToResponse).toList();
    }

    @Override
    public List<AddressResponse> getByUserId(String userId) {
        List<Address> addresses = addressRepository.findAllByUserId(userId);
        return addresses.stream().map(this::parseAddressToResponse).toList();
    }

    @Override
    public AddressResponse getResponseById(String id) {
        Address address = addressRepository.findById(id).orElse(null);
        if (address != null) {
            return parseAddressToResponse(address);
        }
        return null;
    }

    @Override
    public Address getById(String id) {
        return addressRepository.findById(id).orElse(null);
    }

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
        return parseAddressToResponse(address);
    }

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
        addressRepository.saveAndFlush(address);
        return parseAddressToResponse(address);
    }

    @Override
    public void delete(String id) {
        addressRepository.deleteById(id);
    }

    private AddressResponse parseAddressToResponse(Address address) {
        return AddressResponse.builder()
                .id(address.getId())
                .user(parseUserToResponse(address.getUser()))
                .country(address.getCountry())
                .state(address.getState())
                .city(address.getCity())
                .detail(address.getDetail())
                .postalCode(address.getPostalCode())
                .phoneNumber(address.getPhoneNumber())
                .createdAt(address.getCreatedAt())
                .updatedAt(address.getUpdatedAt())
                .build();
    }

    private UserResponse parseUserToResponse(User user){
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .displayName(user.getDisplayName())
                .email(user.getEmail())
                .gender(user.getGender())
                .birthDate(user.getBirthDate())
                .phoneNumber(user.getPhoneNumber())
                .isArtist(user.isArtist())
                .userAccountId(user.getUserAccount().getId())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
