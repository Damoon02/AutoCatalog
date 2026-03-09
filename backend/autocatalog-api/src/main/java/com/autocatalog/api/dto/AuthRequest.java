package com.autocatalog.api.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

// DTO for authentication request containing username and password
public class AuthRequest {
    private String username;
    private String password;
}