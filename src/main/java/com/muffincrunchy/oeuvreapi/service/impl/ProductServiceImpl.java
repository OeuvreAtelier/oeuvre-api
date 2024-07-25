package com.muffincrunchy.oeuvreapi.service.impl;

import com.muffincrunchy.oeuvreapi.model.constant.ItemCategory;
import com.muffincrunchy.oeuvreapi.model.constant.ItemType;
import com.muffincrunchy.oeuvreapi.model.dto.request.CreateProductRequest;
import com.muffincrunchy.oeuvreapi.model.dto.request.PagingRequest;
import com.muffincrunchy.oeuvreapi.model.dto.request.SearchProductRequest;
import com.muffincrunchy.oeuvreapi.model.dto.request.UpdateProductRequest;
import com.muffincrunchy.oeuvreapi.model.dto.response.ImageResponse;
import com.muffincrunchy.oeuvreapi.model.dto.response.ProductResponse;
import com.muffincrunchy.oeuvreapi.model.dto.response.UserResponse;
import com.muffincrunchy.oeuvreapi.model.entity.Image;
import com.muffincrunchy.oeuvreapi.model.entity.Product;
import com.muffincrunchy.oeuvreapi.model.entity.User;
import com.muffincrunchy.oeuvreapi.repository.ProductRepository;
import com.muffincrunchy.oeuvreapi.service.*;
import com.muffincrunchy.oeuvreapi.utils.specification.ProductSpecification;
import com.muffincrunchy.oeuvreapi.utils.validation.Validation;
import jakarta.annotation.PostConstruct;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.muffincrunchy.oeuvreapi.model.constant.ApiUrl.PRODUCT_IMG_URL;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final UserService userService;
    private final CategoryService categoryService;
    private final TypeService typeService;
    private final ImageService imageService;
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
    public Page<ProductResponse> getAll(PagingRequest pagingRequest) {
        String sortBy = "updatedAt";
        if (pagingRequest.getPage() <= 0) {
            pagingRequest.setPage(1);
        }
        Sort sort = Sort.by(Sort.Direction.fromString(pagingRequest.getDirection()), sortBy);
        Pageable pageable = PageRequest.of(pagingRequest.getPage()-1, pagingRequest.getSize(), sort);
        List<Product> products = productRepository.findAll();
        List<ProductResponse> productResponses = products.stream().map(this::parseProductToResponse).toList();
        final int start = (int) pageable.getOffset();
        final int end = Math.min(start + pageable.getPageSize(), productResponses.size());
        return new PageImpl<>(productResponses.subList(start, end), pageable, productResponses.size());
    }

    @Override
    public Page<ProductResponse> getBySearch(PagingRequest pagingRequest, SearchProductRequest request) {
        String sortBy = "updatedAt";
        if (pagingRequest.getPage() <= 0) {
            pagingRequest.setPage(1);
        }
        Sort sort = Sort.by(Sort.Direction.fromString(pagingRequest.getDirection()), sortBy);
        Pageable pageable = PageRequest.of(pagingRequest.getPage()-1, pagingRequest.getSize(), sort);
        Specification<Product> specification = ProductSpecification.getSpecification(request);
        List<Product> products = productRepository.findAll(specification);
        List<ProductResponse> productResponses = products.stream().map(this::parseProductToResponse).toList();
        final int start = (int) pageable.getOffset();
        final int end = Math.min(start + pageable.getPageSize(), productResponses.size());
        return new PageImpl<>(productResponses.subList(start, end), pageable, productResponses.size());
    }

    @Override
    public Page<ProductResponse> getByUser(PagingRequest pagingRequest, String userId) {
        String sortBy = "updatedAt";
        if (pagingRequest.getPage() <= 0) {
            pagingRequest.setPage(1);
        }
        Sort sort = Sort.by(Sort.Direction.fromString(pagingRequest.getDirection()), sortBy);
        Pageable pageable = PageRequest.of(pagingRequest.getPage()-1, pagingRequest.getSize(), sort);
        List<Product> products = productRepository.findAllByUserId(userId);
        List<ProductResponse> productResponses = products.stream().map(this::parseProductToResponse).toList();
        final int start = (int) pageable.getOffset();
        final int end = Math.min(start + pageable.getPageSize(), productResponses.size());
        return new PageImpl<>(productResponses.subList(start, end), pageable, productResponses.size());
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
        if (request.getImage().isEmpty()) {
            throw new ConstraintViolationException("image is required", null);
        }
        Image image = imageService.create(request.getImage());
        Product product = Product.builder()
                .name(request.getName())
                .category(categoryService.getOrSave(ItemCategory.valueOf(request.getCategory())))
                .price(request.getPrice())
                .stock(request.getStock())
                .user(userService.getById(request.getUserId()))
                .type(typeService.getOrSave(ItemType.valueOf(request.getType())))
                .createdAt(new Date())
                .updatedAt(new Date())
                .image(image)
                .build();
        productRepository.saveAndFlush(product);
        return parseProductToResponse(product);
    }

    @Override
    public ProductResponse update(UpdateProductRequest request) {
        validation.validate(request);
        Product product = getById(request.getId());
        if (request.getImage() != null) {
            String oldImageId = product.getImage().getId();
            Image newImage = imageService.create(request.getImage());
            product.setImage(newImage);
            imageService.deleteById(oldImageId);
        }
        product.setName(request.getName());
        product.setCategory(categoryService.getOrSave(ItemCategory.valueOf(request.getCategory())));
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setType(typeService.getOrSave(ItemType.valueOf(request.getType())));
        product.setUpdatedAt(new Date());
        productRepository.saveAndFlush(product);
        return parseProductToResponse(product);
    }

    @Override
    public void delete(String id) {
        String imgId = getById(id).getImage().getId();
        productRepository.deleteById(id);
        imageService.deleteById(imgId);
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
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .image(ImageResponse.builder()
                        .url(PRODUCT_IMG_URL + product.getImage().getId())
                        .path(product.getImage().getPath())
                        .name(product.getImage().getName())
                        .build())
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
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}