package com.proyect.tech.Repository;

import com.proyect.tech.Model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByIdCard(String idCard);

	boolean existsByIdCard(String idCard);

	Optional<User> findByAuthEmail(String email);
}
