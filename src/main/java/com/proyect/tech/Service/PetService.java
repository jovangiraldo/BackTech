package com.proyect.tech.Service;

import com.proyect.tech.Model.Pet;
import java.util.List;

public interface PetService {

    Iterable<Pet> findAll();

    Pet findByIdOrThrow(Long id);

    Pet save(Pet pet);

    void deleteById(Long id);

    boolean existsById(Long id);

    List<Pet> findAllByUserId(Long userId);

    List<Pet> findAllByAnimalTypeId(Long animalTypeId);

    List<Pet> findAllByBreedId(Long breedId);
}
