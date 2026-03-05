package com.proyect.tech.Service;

import com.proyect.tech.Model.User;
import java.util.Optional;

public interface UserService {

	Iterable<User> findAll();

	User findByIdOrThrow(Long id);

	User save(User user);

	void deleteById(Long id);

	boolean existsById(Long id);

	Optional<User> findByIdCard(String idCard);

	boolean existsByIdCard(String idCard);

	Optional<User> findByAuthEmail(String email);
}
