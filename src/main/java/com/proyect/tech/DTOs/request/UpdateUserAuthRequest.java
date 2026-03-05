package com.proyect.tech.DTOs.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateUserAuthRequest(
        @Email @NotBlank String email,
        @NotBlank String passwordHash,
        @NotNull Long userId
) {
}
