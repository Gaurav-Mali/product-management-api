package com.zestindia.productmanagement.service;

import com.zestindia.productmanagement.dto.request.LoginRequest;
import com.zestindia.productmanagement.dto.request.RegisterRequest;
import com.zestindia.productmanagement.dto.request.RefreshTokenRequest;
import com.zestindia.productmanagement.dto.response.TokenResponse;
import com.zestindia.productmanagement.entity.RefreshToken;
import com.zestindia.productmanagement.entity.Role;
import com.zestindia.productmanagement.entity.User;
import com.zestindia.productmanagement.repository.UserRepository;
import com.zestindia.productmanagement.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    // 1. Inject the new RefreshTokenService
    private final RefreshTokenService refreshTokenService;

    public TokenResponse register(RegisterRequest request) {
        var user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        repository.save(user);

        var jwtToken = jwtService.generateToken(user);
        // 2. Generate the refresh token
        var refreshToken = refreshTokenService.createRefreshToken(user.getEmail());

        return TokenResponse.builder()
                .token(jwtToken)
                .refreshToken(refreshToken.getToken()) // 3. Add to response
                .build();
    }

    public TokenResponse authenticate(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();

        var jwtToken = jwtService.generateToken(user);
        // 2. Generate the refresh token
        var refreshToken = refreshTokenService.createRefreshToken(user.getEmail());

        return TokenResponse.builder()
                .token(jwtToken)
                .refreshToken(refreshToken.getToken()) // 3. Add to response
                .build();
    }

    // 4. New method to handle the token refresh process
    public TokenResponse refreshToken(RefreshTokenRequest request) {
        return refreshTokenService.findByToken(request.getToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String accessToken = jwtService.generateToken(user);
                    return TokenResponse.builder()
                            .token(accessToken)
                            .refreshToken(request.getToken()) // Return the original refresh token
                            .build();
                })
                .orElseThrow(() -> new RuntimeException("Refresh token is not in database!"));
    }
}