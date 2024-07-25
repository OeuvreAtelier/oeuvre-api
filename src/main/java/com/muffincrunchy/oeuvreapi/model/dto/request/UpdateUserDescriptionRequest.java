package com.muffincrunchy.oeuvreapi.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateUserDescriptionRequest {

    @NotBlank(message = "id is required")
    private String id;

    @NotBlank(message = "description is required")
    private String description;

    private String twitter;

    private String pixiv;
}
