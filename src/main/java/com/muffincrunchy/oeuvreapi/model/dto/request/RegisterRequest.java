package com.muffincrunchy.oeuvreapi.model.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterRequest {
    private String username;
    private String name;
    private String password;
}
