package com.muffincrunchy.oeuvreapi.utils.parsing;

import com.muffincrunchy.oeuvreapi.model.dto.response.*;
import com.muffincrunchy.oeuvreapi.model.entity.*;

import static com.muffincrunchy.oeuvreapi.model.constant.ApiUrl.IMG_URL;

public class ToResponse {

    public static ProductReviewResponse parseProductReview(ProductReview productReview) {
        return ProductReviewResponse.builder()
                .id(productReview.getId())
                .user(parseUser(productReview.getUser()))
                .product(parseProduct(productReview.getProduct()))
                .rating(productReview.getRating())
                .review(productReview.getReview())
                .createdAt(productReview.getCreatedAt())
                .build();
    }

    public static ProductDescriptionResponse parseProductDescription(ProductDescription productDescription) {
        return ProductDescriptionResponse.builder()
                .id(productDescription.getId())
                .description(productDescription.getDescription())
                .createdAt(productDescription.getCreatedAt())
                .updatedAt(productDescription.getUpdatedAt())
                .build();
    }

    public static UserDescriptionResponse parseUserDescription(UserDescription userDescription) {
        return UserDescriptionResponse.builder()
                .id(userDescription.getId())
                .description(userDescription.getDescription())
                .pixiv(userDescription.getPixiv())
                .createdAt(userDescription.getCreatedAt())
                .updatedAt(userDescription.getUpdatedAt())
                .build();
    }

    public static AddressResponse parseAddress(Address address) {
        return AddressResponse.builder()
                .id(address.getId())
                .user(parseUser(address.getUser()))
                .country(address.getCountry())
                .state(address.getState())
                .city(address.getCity())
                .detail(address.getDetail())
                .postalCode(address.getPostalCode())
                .phoneNumber(address.getPhoneNumber())
                .createdAt(address.getCreatedAt())
                .updatedAt(address.getUpdatedAt())
                .build();
    }

    public static ProductResponse parseProduct(Product product) {
        ProductDescriptionResponse description = null;
        ImageResponse image = null;
        String category = null;
        String type = null;
        if (product.getDescription() != null) {
            description = parseProductDescription(product.getDescription());
        }
        if (product.getImage() != null) {
            image = ImageResponse.builder()
                    .url(IMG_URL + product.getImage().getId())
                    .path(product.getImage().getPath())
                    .name(product.getImage().getName())
                    .build();
        }
        if (product.getCategory() != null) {
            category = product.getCategory().getCategory().toString();
        }
        if (product.getType() != null) {
            type = product.getType().getType().toString();
        }
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .stock(product.getStock())
                .user(parseUser(product.getUser()))
                .category(category)
                .type(type)
                .description(description)
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .image(image)
                .build();
    }

    public static UserResponse parseUser(User user){
        String userId = null;
        String gender = null;
        ImageResponse image = null;
        UserDescriptionResponse description = null;
        if(user.getUserAccount() != null){
            userId = user.getUserAccount().getId();
        }
        if (user.getGender() != null) {
            gender = user.getGender().getGender().toString();
        }
        if (user.getImage() != null) {
            image = ImageResponse.builder()
                    .url(IMG_URL + user.getImage().getId())
                    .path(user.getImage().getPath())
                    .name(user.getImage().getName())
                    .build();
        }
        if (user.getDescription() != null) {
            description = parseUserDescription(user.getDescription());
        }
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .displayName(user.getDisplayName())
                .email(user.getEmail())
                .gender(gender)
                .birthDate(user.getBirthDate())
                .phoneNumber(user.getPhoneNumber())
                .description(description)
                .isArtist(user.isArtist())
                .userAccountId(userId)
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .image(image)
                .build();
    }
}
