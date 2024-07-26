package com.muffincrunchy.oeuvreapi.service.impl;

import com.muffincrunchy.oeuvreapi.model.entity.ProductDescription;
import com.muffincrunchy.oeuvreapi.repository.ProductDescriptionRepository;
import com.muffincrunchy.oeuvreapi.service.ProductDescriptionService;
import com.muffincrunchy.oeuvreapi.utils.validation.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductDescriptionServiceImpl implements ProductDescriptionService {

    private final ProductDescriptionRepository productDescriptionRepository;
    private final Validation validation;

    @Override
    public List<ProductDescription> getAll() {
        return productDescriptionRepository.findAll();
    }

    @Override
    public ProductDescription getById(String id) {
        return productDescriptionRepository.findById(id).orElse(null);
    }

    @Override
    public ProductDescription create(ProductDescription request) {
        validation.validate(request);
        return productDescriptionRepository.saveAndFlush(request);
    }

    @Override
    public ProductDescription update(ProductDescription request) {
        validation.validate(request);
        ProductDescription productDescription = getById(request.getId());
        productDescription.setDescription(request.getDescription());
        productDescription.setUpdatedAt(new Date());
        return productDescriptionRepository.saveAndFlush(productDescription);
    }

    @Override
    public void delete(String id) {
        productDescriptionRepository.deleteById(id);
    }
}
