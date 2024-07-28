package com.muffincrunchy.oeuvreapi.service.impl;

import com.muffincrunchy.oeuvreapi.model.dto.request.CreateStoreRequest;
import com.muffincrunchy.oeuvreapi.model.dto.request.UpdateStoreRequest;
import com.muffincrunchy.oeuvreapi.model.dto.response.StoreResponse;
import com.muffincrunchy.oeuvreapi.model.entity.Store;
import com.muffincrunchy.oeuvreapi.repository.StoreRepository;
import com.muffincrunchy.oeuvreapi.service.AddressService;
import com.muffincrunchy.oeuvreapi.service.StoreService;
import com.muffincrunchy.oeuvreapi.service.UserService;
import com.muffincrunchy.oeuvreapi.utils.parsing.ToResponse;
import com.muffincrunchy.oeuvreapi.utils.validation.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;
    private final UserService userService;
    private final AddressService addressService;
    private final Validation validation;

    @Override
    public List<StoreResponse> getAll() {
        List<Store> stores = storeRepository.findAll();
        return stores.stream().map(ToResponse::parseStore).toList();
    }

    @Override
    public Store getById(String id) {
        return storeRepository.findById(id).orElse(null);
    }

    @Override
    public StoreResponse getResponseById(String id) {
        Store store = storeRepository.findById(id).orElse(null);
        if (store != null) {
            return ToResponse.parseStore(store);
        }
        return null;
    }

    @Override
    public StoreResponse getByUserId(String userId) {
        Store store = storeRepository.findByUserId(userId).orElse(null);
        if (store != null) {
            return ToResponse.parseStore(store);
        }
        return null;
    }

    @Override
    public StoreResponse create(CreateStoreRequest request) {
        validation.validate(request);
        Store store = Store.builder()
                .user(userService.getById(request.getUserId()))
                .address(addressService.getById(request.getAddressId()))
                .description(request.getDescription())
                .pixiv(request.getPixiv())
                .twitter(request.getTwitter())
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();
        storeRepository.saveAndFlush(store);
        userService.saveStore(request.getUserId(), store.getId());
        return ToResponse.parseStore(store);
    }

    @Override
    public StoreResponse update(UpdateStoreRequest request) {
        validation.validate(request);
        Store store = getById(request.getId());
        store.setAddress(addressService.getById(request.getAddressId()));
        store.setDescription(request.getDescription());
        store.setPixiv(request.getPixiv());
        store.setTwitter(request.getTwitter());
        store.setUpdatedAt(new Date());
        storeRepository.saveAndFlush(store);
        return ToResponse.parseStore(store);
    }

    @Override
    public void delete(String id) {
        storeRepository.deleteById(id);
    }
}
