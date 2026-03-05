package com.proyect.tech.DTOs.request;

import jakarta.validation.constraints.NotBlank;

public record CreateAnimalTypeRequest(
        @NotBlank String name
) {
}
