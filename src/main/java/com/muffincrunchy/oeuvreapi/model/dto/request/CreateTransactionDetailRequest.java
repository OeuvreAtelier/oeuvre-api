package com.muffincrunchy.oeuvreapi.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateTransactionDetailRequest {

    @NotBlank(message = "product_id is required")
    @JsonProperty("product_id")
    private String productId;

    @NotNull(message = "quantity is required")
    private Integer quantity;
}
