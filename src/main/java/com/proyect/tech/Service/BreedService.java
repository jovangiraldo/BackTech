package com.proyect.tech.Service;

import com.proyect.tech.Model.Breed;
import java.util.List;
import java.util.Optional;

public interface BreedService {

    Iterable<Breed> findAll();

    Breed findByIdOrThrow(Long id);

    Breed save(Breed breed);

    void deleteById(Long id);

    boolean existsById(Long id);

    List<Breed> findAllByAnimalTypeId(Long animalTypeId);

    Optional<Breed> findByNameIgnoreCaseAndAnimalTypeId(String name, Long animalTypeId);

    boolean existsByNameIgnoreCaseAndAnimalTypeId(String name, Long animalTypeId);
}
