package com.proyect.tech.Controller;

import com.proyect.tech.DTO.LoginRequest;
import com.proyect.tech.DTO.LoginResponse;
import com.proyect.tech.DTO.CreateUserDTO;
import com.proyect.tech.DTO.UserDTO;
import com.proyect.tech.Service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ── Público: login ────────────────────────────────────
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(userService.login(request));
    }

    // ── Solo ADMINISTRADOR: crear usuario del sistema ─────
    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<UserDTO> create(@RequestBody CreateUserDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.crearUsuario(dto));
    }

    // ── Solo ADMINISTRADOR o ASISTENTE: ver todos ─────────
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'ASISTENTE')")
    public ResponseEntity<List<UserDTO>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    // ── Solo ADMINISTRADOR o ASISTENTE: ver por ID ────────
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'ASISTENTE')")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(
            userService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Usuario no encontrado"))
        );
    }

    // ── Solo ADMINISTRADOR: actualizar ────────────────────
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<UserDTO> update(@PathVariable Long id,
                                          @RequestBody CreateUserDTO dto) {
        return ResponseEntity.ok(userService.update(id, dto));
    }

    // ── Solo ADMINISTRADOR: eliminar ──────────────────────
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.ok("Usuario eliminado correctamente");
    }
}