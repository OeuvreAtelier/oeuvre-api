package com.muffincrunchy.oeuvreapi.utils.parsing;

import com.muffincrunchy.oeuvreapi.model.dto.response.*;
import com.muffincrunchy.oeuvreapi.model.entity.*;

import java.util.List;

public class ToResponse {

    public static PaymentResponse parsePayment(Payment payment) {
        return PaymentResponse.builder()
                .id(payment.getId())
                .token(payment.getToken())
                .redirectUrl(payment.getRedirectUrl())
                .transactionStatus(payment.getTransactionStatus())
                .build();
    }

    public static TransactionDetailResponse parseTransactionDetail(TransactionDetail transactionDetail) {
        return TransactionDetailResponse.builder()
                .id(transactionDetail.getId())
                .invoice(transactionDetail.getInvoice())
                .product(parseProduct(transactionDetail.getProduct()))
                .quantity(transactionDetail.getQuantity())
                .build();
    }

    public static TransactionResponse parseTransaction(Transaction transaction) {
        List<TransactionDetailResponse> transactionDetails = transaction.getTransactionDetails().stream().map(ToResponse::parseTransactionDetail).toList();
        PaymentResponse payment = parsePayment(transaction.getPayment());
        return TransactionResponse.builder()
                .id(transaction.getId())
                .address(parseAddress(transaction.getAddress()))
                .user(parseUser(transaction.getUser()))
                .transactionDate(transaction.getTransactionDate())
                .transactionDetails(transactionDetails)
                .payment(payment)
                .build();
    }

    public static ProductReviewResponse parseProductReview(ProductReview productReview) {
        return ProductReviewResponse.builder()
                .id(productReview.getId())
                .transactionDetail(parseTransactionDetail(productReview.getTransactionDetail()))
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

    public static StoreResponse parseStore(Store store) {
        return StoreResponse.builder()
                .id(store.getId())
                .description(store.getDescription())
                .pixiv(store.getPixiv())
                .twitter(store.getTwitter())
                .address(parseAddressStore(store.getAddress()))
                .createdAt(store.getCreatedAt())
                .updatedAt(store.getUpdatedAt())
                .build();
    }

    public static AddressStoreResponse parseAddressStore(Address address) {
        return AddressStoreResponse.builder()
                .id(address.getId())
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
        ImageResponse imagePicture = null;
        ImageResponse imageBanner = null;
        StoreResponse store = null;
        if(user.getUserAccount() != null){
            userId = user.getUserAccount().getId();
        }
        if (user.getGender() != null) {
            gender = user.getGender().getGender().toString();
        }
        if (user.getImagePicture() != null) {
            imagePicture = ImageResponse.builder()
                    .path(user.getImagePicture().getPath())
                    .name(user.getImagePicture().getName())
                    .build();
        }
        if (user.getImageBanner() != null) {
            imageBanner = ImageResponse.builder()
                    .path(user.getImageBanner().getPath())
                    .name(user.getImageBanner().getName())
                    .build();
        }
        if (user.getStore() != null) {
            store = parseStore(user.getStore());
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
                .isArtist(user.isArtist())
                .store(store)
                .userAccountId(userId)
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .imagePicture(imagePicture)
                .imageBanner(imageBanner)
                .build();
    }
}
