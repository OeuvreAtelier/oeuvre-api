package com.muffincrunchy.oeuvreapi.service;

import com.muffincrunchy.oeuvreapi.model.entity.UserDescription;

import java.util.List;

public interface UserDescriptionService {

    List<UserDescription> getAll();
    UserDescription getById(String id);
    UserDescription create(UserDescription request);
    UserDescription update(UserDescription request);
    void delete(String id);
}
