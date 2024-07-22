package com.muffincrunchy.oeuvreapi.service;

import com.muffincrunchy.oeuvreapi.model.constant.ItemCategory;
import com.muffincrunchy.oeuvreapi.model.entity.Category;

import java.util.List;

public interface CategoryService {

    Category getOrSave(ItemCategory itemCategory);
    List<Category> getAll();
}
