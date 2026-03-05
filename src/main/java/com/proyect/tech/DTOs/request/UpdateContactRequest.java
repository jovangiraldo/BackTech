package com.proyect.tech.DTOs.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateContactRequest(
        @NotBlank String phone,
        @NotBlank String address,
        @NotNull Long userId
) {
}
