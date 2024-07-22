package com.muffincrunchy.oeuvreapi.service.impl;

import com.muffincrunchy.oeuvreapi.model.constant.ItemCategory;
import com.muffincrunchy.oeuvreapi.model.entity.Category;
import com.muffincrunchy.oeuvreapi.repository.CategoryRepository;
import com.muffincrunchy.oeuvreapi.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category getOrSave(ItemCategory itemCategory) {
        return categoryRepository.findByCategory(itemCategory).orElseGet(() -> categoryRepository.saveAndFlush(Category.builder().category(itemCategory).build()));
    }

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }
}
