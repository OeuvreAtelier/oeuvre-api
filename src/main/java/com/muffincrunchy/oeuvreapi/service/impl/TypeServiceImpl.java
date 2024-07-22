package com.muffincrunchy.oeuvreapi.service.impl;

import com.muffincrunchy.oeuvreapi.model.constant.ItemType;
import com.muffincrunchy.oeuvreapi.model.entity.Type;
import com.muffincrunchy.oeuvreapi.repository.TypeRepository;
import com.muffincrunchy.oeuvreapi.service.TypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TypeServiceImpl implements TypeService {

    private final TypeRepository typeRepository;

    @Override
    public Type getOrSave(ItemType itemType) {
        return typeRepository.findByType(itemType).orElseGet(() -> typeRepository.saveAndFlush(Type.builder().type(itemType).build()));
    }

    @Override
    public List<Type> getAll() {
        return typeRepository.findAll();
    }
}
