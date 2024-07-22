package com.muffincrunchy.oeuvreapi.service;

import com.muffincrunchy.oeuvreapi.model.constant.ItemType;
import com.muffincrunchy.oeuvreapi.model.entity.Type;

import java.util.List;

public interface TypeService {

    Type getOrSave(ItemType itemType);
    List<Type> getAll();
}
