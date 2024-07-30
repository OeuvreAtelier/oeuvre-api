package com.muffincrunchy.oeuvreapi.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentProductDetailRequest {

    @NotBlank(message = "name is required")
    private String name;

    @NotBlank(message = "price is required")
    private Long price;

    @NotBlank(message = "quantity is required")
    private Integer quantity;
}
