package com.muffincrunchy.oeuvreapi.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateStoreRequest {

    @NotBlank(message = "user_id is required")
    @JsonProperty("user_id")
    private String userId;

    @NotBlank(message = "address_id is required")
    @JsonProperty("address_id")
    private String addressId;

    private String description;

    private String pixiv;

    private String twitter;
}
