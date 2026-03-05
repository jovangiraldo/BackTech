package com.proyect.tech.DTOs.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdatePetRequest(
        @NotBlank String name,
        String description,
        @NotNull Long userId,
        @NotNull Long animalTypeId,
        @NotNull Long breedId
) {
}
