package com.muffincrunchy.oeuvreapi.service.impl;

import com.muffincrunchy.oeuvreapi.model.entity.UserDescription;
import com.muffincrunchy.oeuvreapi.repository.UserDescriptionRepository;
import com.muffincrunchy.oeuvreapi.service.UserDescriptionService;
import com.muffincrunchy.oeuvreapi.utils.validation.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserDescriptionServiceImpl implements UserDescriptionService {

    private final UserDescriptionRepository userDescriptionRepository;
    private final Validation validation;

    @Override
    public List<UserDescription> getAll() {
        return userDescriptionRepository.findAll();
    }

    @Override
    public UserDescription getById(String id) {
        return userDescriptionRepository.findById(id).orElse(null);
    }

    @Override
    public UserDescription create(UserDescription request) {
        validation.validate(request);
        UserDescription userDescription = UserDescription.builder()
                .description(request.getDescription())
                .pixiv(request.getPixiv())
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();
        return userDescriptionRepository.saveAndFlush(userDescription);
    }

    @Override
    public UserDescription update(UserDescription request) {
        validation.validate(request);
        UserDescription userDescription = getById(request.getId());
        userDescription.setDescription(request.getDescription());
        userDescription.setPixiv(request.getPixiv());
        userDescription.setUpdatedAt(new Date());
        return userDescriptionRepository.saveAndFlush(userDescription);
    }

    @Override
    public void delete(String id) {
        userDescriptionRepository.deleteById(id);
    }
}
