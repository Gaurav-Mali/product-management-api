package com.zestindia.productmanagement.controller;

import com.zestindia.productmanagement.dto.request.RefreshTokenRequest;
import com.zestindia.productmanagement.dto.request.LoginRequest;
import com.zestindia.productmanagement.dto.request.RegisterRequest;
import com.zestindia.productmanagement.dto.response.TokenResponse;
import com.zestindia.productmanagement.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<TokenResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> authenticate(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }
    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok(authService.refreshToken(request));
    }
}