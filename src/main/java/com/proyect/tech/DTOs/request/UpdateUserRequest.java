package com.proyect.tech.DTOs.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import java.time.LocalDateTime;

public record UpdateUserRequest(
        @NotBlank String name,
        @NotBlank String firstLastName,
        @NotBlank String secondLastName,
        @NotBlank String idCard,
        @Past LocalDateTime birthDate
) {
}
