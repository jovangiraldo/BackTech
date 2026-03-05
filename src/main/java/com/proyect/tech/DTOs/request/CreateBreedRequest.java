package com.proyect.tech.DTOs.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateBreedRequest(
        @NotBlank String name,
        @NotNull Long animalTypeId
) {
}
