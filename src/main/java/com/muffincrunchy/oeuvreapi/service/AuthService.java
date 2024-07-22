package com.muffincrunchy.oeuvreapi.service;

import com.muffincrunchy.oeuvreapi.model.dto.request.AuthRequest;
import com.muffincrunchy.oeuvreapi.model.dto.request.RegisterRequest;
import com.muffincrunchy.oeuvreapi.model.dto.response.LoginResponse;
import com.muffincrunchy.oeuvreapi.model.dto.response.RegisterResponse;

public interface AuthService {
    RegisterResponse register(RegisterRequest request);
    RegisterResponse registerAdmin(AuthRequest request);
    LoginResponse login(AuthRequest request);
}