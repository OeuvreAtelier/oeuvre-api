package com.muffincrunchy.oeuvreapi.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentDetailRequest {

    @NotBlank(message = "order_id is required")
    @JsonProperty("order_id")
    private String orderId;

    @NotBlank(message = "gross_amount is required")
    @JsonProperty("gross_amount")
    private Long grossAmount;
}
