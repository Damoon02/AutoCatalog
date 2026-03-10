package com.autocatalog.api.service;

import com.autocatalog.api.dto.AuthRequest;
import com.autocatalog.api.dto.AuthResponse;
import com.autocatalog.api.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service // Service for handling authentication logic, including validating credentials and generating JWT tokens
@RequiredArgsConstructor // Lombok annotation to generate a constructor for final fields
public class AuthService {

    // Services for handling authentication, JWT operations, and loading user details, injected via constructor
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    // Method to authenticate the user and generate a JWT token if the credentials are valid
    public AuthResponse login(AuthRequest request) {
    authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    request.getUsername(),
                    request.getPassword()
            )
    );

    UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
    String token = jwtService.generateToken(userDetails);

    String role = userDetails.getAuthorities()
            .stream()
            .findFirst()
            .map(auth -> auth.getAuthority().replace("ROLE_", ""))
            .orElse("USER");

    return AuthResponse.builder()
            .token(token)
            .username(userDetails.getUsername())
            .role(role)
            .build();
}
}