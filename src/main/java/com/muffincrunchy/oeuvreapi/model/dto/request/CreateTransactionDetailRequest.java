package com.muffincrunchy.oeuvreapi.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateTransactionDetailRequest {

    @NotBlank(message = "merch_id is required")
    @JsonProperty("merch_id")
    private String merchId;

    @NotBlank(message = "shipment_id is required")
    @JsonProperty("shipment_id")
    private String shipmentId;

    @NotNull(message = "qty is required")
    private Integer qty;

    @NotBlank(message = "payment is required")
    @JsonProperty("payment")
    private String payment;
}
