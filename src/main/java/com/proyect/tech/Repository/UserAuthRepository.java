package com.proyect.tech.Repository;

import com.proyect.tech.Model.UserAuth;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAuthRepository extends JpaRepository<UserAuth, Long> {

	Optional<UserAuth> findByEmail(String email);

	boolean existsByEmail(String email);

	Optional<UserAuth> findByUserId(Long userId);
}
