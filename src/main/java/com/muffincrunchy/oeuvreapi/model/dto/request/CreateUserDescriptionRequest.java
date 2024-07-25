package com.muffincrunchy.oeuvreapi.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateUserDescriptionRequest {

    @NotBlank(message = "description is required")
    private String description;

    private String twitter;

    private String pixiv;
}
