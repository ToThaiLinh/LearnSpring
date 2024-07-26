package com.example.SpringJPA.controller;

import com.example.SpringJPA.dto.request.*;
import com.example.SpringJPA.dto.response.AuthenticationResponse;
import com.example.SpringJPA.dto.response.IntrospectResponse;
import com.example.SpringJPA.dto.response.RefreshTokenResponse;
import com.example.SpringJPA.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("/token")
    public ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse auth = authenticationService.authenticated(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(auth)
                .build();
    }
    @PostMapping("/introspect")
    public ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
        IntrospectResponse result = authenticationService.introspect(request.getToken());
        return ApiResponse.<IntrospectResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/logout")
    public ApiResponse<String> logout(@RequestBody LogoutRequest request) throws ParseException, JOSEException {
        authenticationService.logout(request);
        return ApiResponse.<String>builder()
                .result("Logout success")
                .build();
    }

    @PostMapping("/refresh")
    public ApiResponse<RefreshTokenResponse> refreshToken(@RequestBody RefreshTokenRequest request) throws ParseException, JOSEException {
        return ApiResponse.<RefreshTokenResponse>builder()
                .result(authenticationService.refreshToken(request))
                .build();
    }

}
