package com.muffincrunchy.oeuvreapi.service;

import com.muffincrunchy.oeuvreapi.model.dto.request.AuthRequest;
import com.muffincrunchy.oeuvreapi.model.dto.request.RegisterRequest;
import com.muffincrunchy.oeuvreapi.model.dto.response.LoginResponse;
import com.muffincrunchy.oeuvreapi.model.dto.response.RegisterResponse;

public interface AuthService {
    RegisterResponse registerCustomer(RegisterRequest request);
    RegisterResponse registerArtist();
    RegisterResponse registerAdmin(AuthRequest request);
    LoginResponse login(AuthRequest request);
}