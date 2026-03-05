package com.proyect.tech.Controller;

import com.proyect.tech.DTOs.response.AnimalTypeResponse;
import com.proyect.tech.DTOs.request.CreateAnimalTypeRequest;
import com.proyect.tech.DTOs.request.UpdateAnimalTypeRequest;
import com.proyect.tech.Model.AnimalType;
import com.proyect.tech.Service.AnimalTypeService;
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
@RequestMapping("/animal-types")
public class AnimalTypeController {

    private final AnimalTypeService animalTypeService;

    public AnimalTypeController(AnimalTypeService animalTypeService) {
        this.animalTypeService = animalTypeService;
    }

    @GetMapping
    public ResponseEntity<List<AnimalTypeResponse>> findAll() {
        List<AnimalTypeResponse> response = StreamSupport.stream(animalTypeService.findAll().spliterator(), false)
                .map(AnimalTypeResponse::fromEntity)
                .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnimalTypeResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(AnimalTypeResponse.fromEntity(animalTypeService.findByIdOrThrow(id)));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<AnimalTypeResponse> findByName(@PathVariable String name) {
        AnimalType animalType = animalTypeService.findByNameIgnoreCase(name)
                .orElseThrow(() -> new NoSuchElementException("AnimalType not found with name: " + name));

        return ResponseEntity.ok(AnimalTypeResponse.fromEntity(animalType));
    }

    @PostMapping
    public ResponseEntity<AnimalTypeResponse> create(@Valid @RequestBody CreateAnimalTypeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(AnimalTypeResponse.fromEntity(animalTypeService.save(toAnimalType(request))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnimalTypeResponse> update(@PathVariable Long id, @Valid @RequestBody UpdateAnimalTypeRequest request) {
        animalTypeService.findByIdOrThrow(id);
        return ResponseEntity.ok(AnimalTypeResponse.fromEntity(animalTypeService.save(toAnimalType(id, request))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        animalTypeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private AnimalType toAnimalType(CreateAnimalTypeRequest request) {
        AnimalType animalType = new AnimalType();
        animalType.setName(request.name());
        return animalType;
    }

    private AnimalType toAnimalType(Long id, UpdateAnimalTypeRequest request) {
        AnimalType animalType = toAnimalType(new CreateAnimalTypeRequest(request.name()));
        animalType.setId(id);
        return animalType;
    }
}

