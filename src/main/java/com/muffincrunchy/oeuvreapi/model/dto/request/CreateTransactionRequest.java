package com.muffincrunchy.oeuvreapi.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CreateTransactionRequest {

    @NotBlank(message = "user_id is required")
    @JsonProperty("user_id")
    private String userId;

    @NotNull(message = "transaction_details is required")
    @JsonProperty("transaction_details")
    private List<CreateTransactionDetailRequest> transactionDetails;
}
