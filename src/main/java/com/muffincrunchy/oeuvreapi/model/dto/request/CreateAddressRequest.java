package com.muffincrunchy.oeuvreapi.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateAddressRequest {

    @NotBlank(message = "user_id is required")
    @JsonProperty("user_id")
    private String userId;

    @NotBlank(message = "country is required")
    private String country;

    @NotBlank(message = "state is required")
    private String state;

    @NotBlank(message = "city is required")
    private String city;

    @NotBlank(message = "detail is required")
    private String detail;

    @NotBlank(message = "postal_code is required")
    @JsonProperty("postal_code")
    private String postalCode;

    @NotBlank(message = "phone_number is required")
    @JsonProperty("phone_number")
    private String phoneNumber;
}
