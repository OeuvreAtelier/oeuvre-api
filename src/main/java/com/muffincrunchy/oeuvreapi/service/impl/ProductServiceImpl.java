package com.muffincrunchy.oeuvreapi.service.impl;

import com.muffincrunchy.oeuvreapi.model.constant.ItemCategory;
import com.muffincrunchy.oeuvreapi.model.constant.ItemType;
import com.muffincrunchy.oeuvreapi.model.dto.request.CreateProductRequest;
import com.muffincrunchy.oeuvreapi.model.dto.request.UpdateProductRequest;
import com.muffincrunchy.oeuvreapi.model.dto.response.ProductResponse;
import com.muffincrunchy.oeuvreapi.model.dto.response.UserResponse;
import com.muffincrunchy.oeuvreapi.model.entity.Product;
import com.muffincrunchy.oeuvreapi.model.entity.User;
import com.muffincrunchy.oeuvreapi.repository.ProductRepository;
import com.muffincrunchy.oeuvreapi.service.*;
import com.muffincrunchy.oeuvreapi.utils.Validation;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final UserService userService;
    private final CategoryService categoryService;
    private final TypeService typeService;
    private final Validation validation;

    @PostConstruct
    public void init() {
        for (ItemCategory itemCategory : ItemCategory.values()) {
            categoryService.getOrSave(itemCategory);
        }
        for (ItemType itemType : ItemType.values()) {
            typeService.getOrSave(itemType);
        }
    }

    @Override
    public List<ProductResponse> getAll() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(this::parseProductToResponse).toList();
    }

    @Override
    public Product getById(String id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public ProductResponse getResponseById(String id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product != null) {
            return parseProductToResponse(product);
        }
        return null;
    }

    @Override
    public ProductResponse create(CreateProductRequest request) {
        validation.validate(request);
        Product product = Product.builder()
                .name(request.getName())
                .category(categoryService.getOrSave(ItemCategory.valueOf(request.getCategory())))
                .price(request.getPrice())
                .stock(request.getStock())
                .user(userService.getById(request.getArtistId()))
                .type(typeService.getOrSave(ItemType.valueOf(request.getType())))
                .build();
        productRepository.saveAndFlush(product);
        return parseProductToResponse(product);
    }

    @Override
    public ProductResponse update(UpdateProductRequest request) {
        validation.validate(request);
        Product product = getById(request.getId());
        product.setName(request.getName());
        product.setCategory(categoryService.getOrSave(ItemCategory.valueOf(request.getCategory())));
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setType(typeService.getOrSave(ItemType.valueOf(request.getType())));
        productRepository.saveAndFlush(product);
        return parseProductToResponse(product);
    }

    @Override
    public void delete(String id) {
        productRepository.deleteById(id);
    }

    private ProductResponse parseProductToResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .stock(product.getStock())
                .user(parseUserToResponse(product.getUser()))
                .category(product.getCategory().getCategory().toString())
                .type(product.getType().getType().toString())
                .build();
    }

    private UserResponse parseUserToResponse(User user){
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .displayName(user.getDisplayName())
                .email(user.getEmail())
                .gender(user.getGender())
                .birthDate(user.getBirthDate())
                .phoneNumber(user.getPhoneNumber())
                .isArtist(user.isArtist())
                .userAccountId(user.getUserAccount().getId())
                .build();
    }
}
