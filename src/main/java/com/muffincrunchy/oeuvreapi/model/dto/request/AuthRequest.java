package com.muffincrunchy.oeuvreapi.model.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthRequest {
    private String username;
    private String password;
}

