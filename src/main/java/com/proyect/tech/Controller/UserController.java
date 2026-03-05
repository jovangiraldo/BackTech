package com.proyect.tech.Controller;

import com.proyect.tech.DTOs.response.UserResponse;
import com.proyect.tech.DTOs.request.CreateUserRequest;
import com.proyect.tech.DTOs.request.UpdateUserRequest;
import com.proyect.tech.Model.User;
import com.proyect.tech.Service.UserService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.StreamSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public ResponseEntity<List<UserResponse>> findAll() {
		List<UserResponse> response = StreamSupport.stream(userService.findAll().spliterator(), false)
				.map(UserResponse::fromEntity)
				.toList();
		return ResponseEntity.ok(response);
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
		return ResponseEntity.ok(UserResponse.fromEntity(userService.findByIdOrThrow(id)));
	}

	@GetMapping("/id-card/{idCard}")
	public ResponseEntity<UserResponse> findByIdCard(@PathVariable String idCard) {
		User user = userService.findByIdCard(idCard)
				.orElseThrow(() -> new NoSuchElementException("User not found with idCard: " + idCard));
		return ResponseEntity.ok(UserResponse.fromEntity(user));
	}

	@GetMapping("/email/{email}")
	public ResponseEntity<UserResponse> findByAuthEmail(@PathVariable String email) {
		User user = userService.findByAuthEmail(email)
				.orElseThrow(() -> new NoSuchElementException("User not found with email: " + email));
		return ResponseEntity.ok(UserResponse.fromEntity(user));
	}

	@PostMapping
	public ResponseEntity<UserResponse> create(@Valid @RequestBody CreateUserRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(UserResponse.fromEntity(userService.save(toUser(request))));
	}

	@PutMapping("/{id}")
	public ResponseEntity<UserResponse> update(@PathVariable Long id, @Valid @RequestBody UpdateUserRequest request) {
		userService.findByIdOrThrow(id);
		return ResponseEntity.ok(UserResponse.fromEntity(userService.save(toUser(id, request))));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		userService.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	private User toUser(CreateUserRequest request) {
		User user = new User();
		user.setName(request.name());
		user.setFirstLastName(request.firstLastName());
		user.setSecondLastName(request.secondLastName());
		user.setIdCard(request.idCard());
		user.setBirthDate(request.birthDate());
		return user;
	}

	private User toUser(Long id, UpdateUserRequest request) {
		User user = toUser(new CreateUserRequest(
				request.name(),
				request.firstLastName(),
				request.secondLastName(),
				request.idCard(),
				request.birthDate()
		));
		user.setId(id);
		return user;
	}
}

