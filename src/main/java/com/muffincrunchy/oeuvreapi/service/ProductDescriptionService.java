package com.muffincrunchy.oeuvreapi.service;

import com.muffincrunchy.oeuvreapi.model.entity.ProductDescription;

import java.util.List;

public interface ProductDescriptionService {

    List<ProductDescription> getAll();
    ProductDescription getById(String id);
    ProductDescription create(ProductDescription request);
    ProductDescription update(ProductDescription request);
    void delete(String id);
}
