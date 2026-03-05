package com.proyect.tech.Service;

import com.proyect.tech.Model.AnimalType;
import java.util.Optional;

public interface AnimalTypeService {

    Iterable<AnimalType> findAll();

    AnimalType findByIdOrThrow(Long id);

    AnimalType save(AnimalType animalType);

    void deleteById(Long id);

    boolean existsById(Long id);

    Optional<AnimalType> findByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);
}
