package com.proyect.tech.Repository;

import com.proyect.tech.Model.Breed;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BreedRepository extends JpaRepository<Breed, Long> {

	List<Breed> findAllByAnimalTypeId(Long animalTypeId);

	Optional<Breed> findByNameIgnoreCaseAndAnimalTypeId(String name, Long animalTypeId);

	boolean existsByNameIgnoreCaseAndAnimalTypeId(String name, Long animalTypeId);
}
