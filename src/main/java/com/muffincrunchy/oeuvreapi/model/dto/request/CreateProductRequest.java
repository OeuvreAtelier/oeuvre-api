package com.muffincrunchy.oeuvreapi.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateProductRequest {

    @NotBlank(message = "name is required")
    private String name;

    @NotBlank(message = "category is required")
    private String category;

    @NotNull(message = "price is required")
    private Long price;

    @NotNull(message = "stock is required")
    private Integer stock;

    @NotBlank(message = "artist_id is required")
    @JsonProperty("artist_id")
    private String artistId;

    @NotBlank(message = "type is required")
    private String type;
}
