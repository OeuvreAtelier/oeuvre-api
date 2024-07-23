package com.muffincrunchy.oeuvreapi.service;

import com.muffincrunchy.oeuvreapi.model.dto.request.CreateProductRequest;
import com.muffincrunchy.oeuvreapi.model.dto.request.UpdateProductRequest;
import com.muffincrunchy.oeuvreapi.model.dto.response.ProductResponse;
import com.muffincrunchy.oeuvreapi.model.entity.Product;

import java.util.List;

public interface ProductService {

    List<ProductResponse> getAll();
    ProductResponse getResponseById(String id);
    Product getById(String id);
    ProductResponse create(CreateProductRequest request);
    ProductResponse update(UpdateProductRequest request);
    void delete(String id);
}
