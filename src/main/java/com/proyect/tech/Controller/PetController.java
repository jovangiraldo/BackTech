package com.proyect.tech.Controller;

import com.proyect.tech.DTO.PetDTO;
import com.proyect.tech.Service.PetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/pets")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping   
    public ResponseEntity<List<PetDTO>> getAll() {
        return ResponseEntity.ok(petService.getAll());
    }

    @GetMapping("/client/{clientId}")    
    public ResponseEntity<List<PetDTO>> getByClient(@PathVariable Long clientId) {
        return ResponseEntity.ok(petService.getByClient(clientId));
    }

    @GetMapping("/{id}")  
    public ResponseEntity<PetDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(petService.getById(id));
    }

    @PostMapping
    //@PreAuthorize("hasAnyRole('ADMINISTRADOR', 'ASISTENTE')")
    public ResponseEntity<PetDTO> create(@RequestBody PetDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(petService.create(dto));
    }

    @PutMapping("/{id}")   
    public ResponseEntity<PetDTO> update(@PathVariable Long id,
            @RequestBody PetDTO dto) {
        return ResponseEntity.ok(petService.update(id, dto));
    }

    @DeleteMapping("/{id}")   
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        petService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/by-identifier/{identifier}")
    public ResponseEntity<PetDTO> getByIdentifier(@PathVariable String identifier) {
        return ResponseEntity.ok(petService.getByIdentifier(identifier));
    }
}