package com.muffincrunchy.oeuvreapi.service.impl;

import com.muffincrunchy.oeuvreapi.model.constant.UserGender;
import com.muffincrunchy.oeuvreapi.model.entity.Gender;
import com.muffincrunchy.oeuvreapi.repository.GenderRepository;
import com.muffincrunchy.oeuvreapi.service.GenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GenderServiceImpl implements GenderService {

    private final GenderRepository genderRepository;

    @Override
    public Gender getOrSave(UserGender userGender) {
        return genderRepository.findByGender(userGender).orElseGet(() -> genderRepository.saveAndFlush(Gender.builder().gender(userGender).build()));
    }

    @Override
    public List<Gender> getAll() {
        return genderRepository.findAll();
    }
}
