package com.autocatalog.api.controller;

import com.autocatalog.api.dto.AuthRequest;
import com.autocatalog.api.dto.AuthResponse;
import com.autocatalog.api.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // Controller for handling authentication requests, such as login
@RequestMapping("/auth") // Base path for authentication-related endpoints
@RequiredArgsConstructor // Lombok annotation to generate a constructor for final fields
public class AuthController {

    // Service for handling authentication logic, injected via constructor
    private final AuthService authService;

    // Endpoint for user login, which accepts an authentication request and returns a response containing the JWT token
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}