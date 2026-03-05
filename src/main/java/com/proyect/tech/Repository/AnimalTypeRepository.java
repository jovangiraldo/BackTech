package com.proyect.tech.Repository;

import com.proyect.tech.Model.AnimalType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalTypeRepository extends JpaRepository<AnimalType, Long> {

	Optional<AnimalType> findByNameIgnoreCase(String name);

	boolean existsByNameIgnoreCase(String name);
}
