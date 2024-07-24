package com.muffincrunchy.oeuvreapi.service;

import com.muffincrunchy.oeuvreapi.model.dto.request.CreateProductRequest;
import com.muffincrunchy.oeuvreapi.model.dto.request.PagingRequest;
import com.muffincrunchy.oeuvreapi.model.dto.request.SearchProductRequest;
import com.muffincrunchy.oeuvreapi.model.dto.request.UpdateProductRequest;
import com.muffincrunchy.oeuvreapi.model.dto.response.ProductResponse;
import com.muffincrunchy.oeuvreapi.model.entity.Product;
import org.springframework.data.domain.Page;

public interface ProductService {

    Page<ProductResponse> getAll(PagingRequest pagingRequest);
    Page<ProductResponse> getBySearch(PagingRequest pagingRequest, SearchProductRequest request);
    Page<ProductResponse> getByUser(PagingRequest pagingRequest, String userId);
    ProductResponse getResponseById(String id);
    Product getById(String id);
    ProductResponse create(CreateProductRequest request);
    ProductResponse update(UpdateProductRequest request);
    void delete(String id);
}