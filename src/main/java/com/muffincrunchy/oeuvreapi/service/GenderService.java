package com.muffincrunchy.oeuvreapi.service;

import com.muffincrunchy.oeuvreapi.model.constant.UserGender;
import com.muffincrunchy.oeuvreapi.model.entity.Gender;

import java.util.List;

public interface GenderService {

    Gender getOrSave(UserGender userGender);
    List<Gender> getAll();
}
