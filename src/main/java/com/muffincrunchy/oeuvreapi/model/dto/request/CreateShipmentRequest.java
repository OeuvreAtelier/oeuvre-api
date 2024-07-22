package com.muffincrunchy.oeuvreapi.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateShipmentRequest {

    @NotBlank(message = "name is required")
    private String name;

    @NotNull(message = "fee is required")
    private Long fee;
}
