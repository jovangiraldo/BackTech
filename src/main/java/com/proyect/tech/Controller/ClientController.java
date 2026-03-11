package com.proyect.tech.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.proyect.tech.DTO.ClientDTO;
import com.proyect.tech.Service.ClientService;

@RestController                          // 👈 faltaba
@RequestMapping("/api/clients")          // 👈 faltaba
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<?> register(@RequestBody ClientDTO dto) {
        return ResponseEntity.ok(clientService.register(dto));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'ASISTENTE')")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(clientService.getAll());
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<?> getMe(Authentication auth) {
        return ResponseEntity.ok(clientService.getByEmail(auth.getName()));
    }
}