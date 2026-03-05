package com.proyect.tech.DTOs.response;

import com.proyect.tech.Model.Breed;

public record BreedResponse(
        Long id,
        String name,
        Long animalTypeId,
        String animalTypeName
) {

    public static BreedResponse fromEntity(Breed breed) {
        Long animalTypeId = breed.getAnimalType() != null ? breed.getAnimalType().getId() : null;
        String animalTypeName = breed.getAnimalType() != null ? breed.getAnimalType().getName() : null;

        return new BreedResponse(
                breed.getId(),
                breed.getName(),
                animalTypeId,
                animalTypeName
        );
    }
}
