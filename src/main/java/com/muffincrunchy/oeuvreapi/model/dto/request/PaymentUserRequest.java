package com.muffincrunchy.oeuvreapi.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentUserRequest {

    @NotBlank(message = "first_name is required")
    @JsonProperty("first_name")
    private String firstName;

    @NotBlank(message = "last_name is required")
    @JsonProperty("last_name")
    private String lastName;
}
