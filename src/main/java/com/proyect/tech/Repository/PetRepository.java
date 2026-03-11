package com.proyect.tech.Repository;

import com.proyect.tech.Model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PetRepository extends JpaRepository<Pet, Long> {

    // Buscar por identifier (para la página pública del QR)
Optional<Pet> findByIdentifier(@Param("identifier") String identifier);

    // Buscar todas las mascotas de un cliente
    List<Pet> findByClientId(Long clientId);
}