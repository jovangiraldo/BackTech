package com.proyect.tech.DTOs.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateAnimalTypeRequest(
        @NotBlank String name
) {
}
