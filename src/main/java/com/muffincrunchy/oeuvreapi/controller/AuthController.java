package com.muffincrunchy.oeuvreapi.controller;

import com.muffincrunchy.oeuvreapi.model.dto.request.AuthRequest;
import com.muffincrunchy.oeuvreapi.model.dto.request.RegisterRequest;
import com.muffincrunchy.oeuvreapi.model.dto.response.CommonResponse;
import com.muffincrunchy.oeuvreapi.model.dto.response.LoginResponse;
import com.muffincrunchy.oeuvreapi.model.dto.response.RegisterResponse;
import com.muffincrunchy.oeuvreapi.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.muffincrunchy.oeuvreapi.model.constant.ApiUrl.AUTH_URL;

@RestController
@RequiredArgsConstructor
@RequestMapping(AUTH_URL)
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register-customer")
    public ResponseEntity<CommonResponse<?>> registerUser(@RequestBody RegisterRequest request){
        RegisterResponse register = authService.registerCustomer(request);
        CommonResponse<RegisterResponse> response = CommonResponse.<RegisterResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("successfully save data")
                .data(register)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/register-artist")
    public ResponseEntity<CommonResponse<?>> registerUser(){
        RegisterResponse register = authService.registerArtist();
        CommonResponse<RegisterResponse> response = CommonResponse.<RegisterResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("successfully update data")
                .data(register)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<CommonResponse<?>> login(@RequestBody AuthRequest request){
        LoginResponse loginResponse = authService.login(request);
        CommonResponse<LoginResponse> response = CommonResponse.<LoginResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("login successfully")
                .data(loginResponse)
                .build();
        return ResponseEntity.ok(response);
    }

}
