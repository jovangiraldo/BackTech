package com.proyect.tech.Repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.proyect.tech.Model.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByUserEmail(String email); // 👈 Spring lo genera automáticamente
}