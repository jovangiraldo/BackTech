package com.proyect.tech.Repository;

import com.proyect.tech.Model.Pet;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {

	List<Pet> findAllByUserId(Long userId);

	List<Pet> findAllByAnimalTypeId(Long animalTypeId);

	List<Pet> findAllByBreedId(Long breedId);
}
