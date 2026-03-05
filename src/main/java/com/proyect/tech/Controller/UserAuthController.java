package com.proyect.tech.Controller;

import com.proyect.tech.DTOs.response.UserAuthResponse;
import com.proyect.tech.DTOs.request.CreateUserAuthRequest;
import com.proyect.tech.DTOs.request.UpdateUserAuthRequest;
import com.proyect.tech.Model.UserAuth;
import com.proyect.tech.Service.UserAuthService;
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
@RequestMapping("/user-auth")
public class UserAuthController {

    private final UserAuthService userAuthService;
    private final UserService userService;

    public UserAuthController(UserAuthService userAuthService, UserService userService) {
        this.userAuthService = userAuthService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserAuthResponse>> findAll() {
        List<UserAuthResponse> response = StreamSupport.stream(userAuthService.findAll().spliterator(), false)
                .map(UserAuthResponse::fromEntity)
                .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserAuthResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(UserAuthResponse.fromEntity(userAuthService.findByIdOrThrow(id)));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserAuthResponse> findByEmail(@PathVariable String email) {
        UserAuth userAuth = userAuthService.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("UserAuth not found with email: " + email));

        return ResponseEntity.ok(UserAuthResponse.fromEntity(userAuth));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<UserAuthResponse> findByUserId(@PathVariable Long userId) {
        UserAuth userAuth = userAuthService.findByUserId(userId)
                .orElseThrow(() -> new NoSuchElementException("UserAuth not found with user id: " + userId));

        return ResponseEntity.ok(UserAuthResponse.fromEntity(userAuth));
    }

    @PostMapping
    public ResponseEntity<UserAuthResponse> create(@Valid @RequestBody CreateUserAuthRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(UserAuthResponse.fromEntity(userAuthService.save(toUserAuth(request))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserAuthResponse> update(@PathVariable Long id, @Valid @RequestBody UpdateUserAuthRequest request) {
        userAuthService.findByIdOrThrow(id);
        return ResponseEntity.ok(UserAuthResponse.fromEntity(userAuthService.save(toUserAuth(id, request))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userAuthService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private UserAuth toUserAuth(CreateUserAuthRequest request) {
        UserAuth userAuth = new UserAuth();
        userAuth.setEmail(request.email());
        userAuth.setPasswordHash(request.passwordHash());
        userAuth.setUser(userService.findByIdOrThrow(request.userId()));
        return userAuth;
    }

    private UserAuth toUserAuth(Long id, UpdateUserAuthRequest request) {
        UserAuth userAuth = toUserAuth(new CreateUserAuthRequest(
                request.email(),
                request.passwordHash(),
                request.userId()
        ));
        userAuth.setId(id);
        return userAuth;
    }
}

