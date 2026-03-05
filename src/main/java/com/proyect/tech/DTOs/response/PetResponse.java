package com.proyect.tech.DTOs.response;

import com.proyect.tech.Model.Pet;

public record PetResponse(
        Long id,
        String name,
        String description,
        Long userId,
        Long animalTypeId,
        Long breedId
) {

    public static PetResponse fromEntity(Pet pet) {
        Long userId = pet.getUser() != null ? pet.getUser().getId() : null;
        Long animalTypeId = pet.getAnimalType() != null ? pet.getAnimalType().getId() : null;
        Long breedId = pet.getBreed() != null ? pet.getBreed().getId() : null;

        return new PetResponse(
                pet.getId(),
                pet.getName(),
                pet.getDescription(),
                userId,
                animalTypeId,
                breedId
        );
    }
}
