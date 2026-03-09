package com.autocatalog.api.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service // Service for handling JWT token generation and validation
public class JwtService {

    // Secret key and expiration time for JWT tokens, injected from application properties
    @Value("${jwt.secret}")
    private String secretKeyString;

    // Expiration time in milliseconds for JWT tokens
    @Value("${jwt.expiration}")
    private long jwtExpiration;

    // SecretKey object created from the secret key string
    private SecretKey secretKey;

    // Initialize the SecretKey after the service is constructed
    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(secretKeyString.getBytes(StandardCharsets.UTF_8));
    }

    // Generate a JWT token for the given user details
    public String generateToken(UserDetails userDetails) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + jwtExpiration);

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(now)
                .expiration(expirationDate)
                .signWith(secretKey)
                .compact();
    }

    // Extract the username from the given JWT token
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // Validate the given JWT token against the user details
    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    // Check if the given JWT token is expired
    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    // Extract all claims from the given JWT token
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}