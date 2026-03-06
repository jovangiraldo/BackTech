package com.proyect.tech.DTOs.response;

public record AuthResponse(
        String token,
        String tokenType,
        long expiresIn
) {
}
