package com.muffincrunchy.oeuvreapi.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PaymentRequest {

    @NotNull(message = "transaction_details is required")
    @JsonProperty("transaction_details")
    private PaymentDetailRequest paymentDetail;

    @NotNull(message = "item_details is required")
    @JsonProperty("item_details")
    private List<PaymentProductDetailRequest> paymentProductDetails;

    @NotNull(message = "enabled_payments is required")
    @JsonProperty("enabled_payments")
    private List<String> paymentMethods;

    @NotNull(message = "customer_details is required")
    @JsonProperty("customer_details")
    private PaymentUserRequest user;
}
