package com.muffincrunchy.oeuvreapi.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@Builder
public class UpdateUserRequest {

    @NotBlank(message = "id is required")
    private String id;

    @NotBlank(message = "first_name is required")
    @JsonProperty("first_name")
    private String firstName;

    @NotBlank(message = "last_name is required")
    @JsonProperty("last_name")
    private String lastName;

    @NotBlank(message = "display_name is required")
    @JsonProperty("display_name")
    private String displayName;

    @NotBlank(message = "email is required")
    private String email;

    @NotBlank(message = "gender is required")
    private String gender;

    @NotNull(message = "birth_date is required")
    @JsonProperty("birth_date")
    private Date birthDate;

    @NotBlank(message = "phone_number is required")
    @JsonProperty("phone_number")
    private String phoneNumber;

    private String description;

    private String pixiv;

    private MultipartFile image;
}
