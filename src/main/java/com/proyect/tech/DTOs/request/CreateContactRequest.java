package com.proyect.tech.DTOs.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateContactRequest(
        @NotBlank String phone,
        @NotBlank String address,
        @NotNull Long userId
) {
}
