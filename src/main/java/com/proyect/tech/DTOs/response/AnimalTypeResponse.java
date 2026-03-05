package com.proyect.tech.DTOs.response;

import com.proyect.tech.Model.AnimalType;

public record AnimalTypeResponse(
        Long id,
        String name
) {

    public static AnimalTypeResponse fromEntity(AnimalType animalType) {
        return new AnimalTypeResponse(animalType.getId(), animalType.getName());
    }
}
