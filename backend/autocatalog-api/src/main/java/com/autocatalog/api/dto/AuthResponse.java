package com.autocatalog.api.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

// DTO for authentication response containing the generated token
public class AuthResponse {
    private String token;
}