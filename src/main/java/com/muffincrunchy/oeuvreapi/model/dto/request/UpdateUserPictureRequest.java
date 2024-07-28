package com.muffincrunchy.oeuvreapi.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class UpdateUserPictureRequest {

    @NotBlank(message = "user_id is required")
    @JsonProperty("user_id")
    private String userId;

    private MultipartFile image;
}
