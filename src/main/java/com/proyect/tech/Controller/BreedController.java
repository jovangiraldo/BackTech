package com.proyect.tech.Controller;

import com.proyect.tech.DTOs.response.BreedResponse;
import com.proyect.tech.DTOs.request.CreateBreedRequest;
import com.proyect.tech.DTOs.request.UpdateBreedRequest;
import com.proyect.tech.Model.Breed;
import com.proyect.tech.Service.AnimalTypeService;
import com.proyect.tech.Service.BreedService;
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
@RequestMapping("/breeds")
public class BreedController {

    private final BreedService breedService;
    private final AnimalTypeService animalTypeService;

    public BreedController(BreedService breedService, AnimalTypeService animalTypeService) {
        this.breedService = breedService;
        this.animalTypeService = animalTypeService;
    }

    @GetMapping
    public ResponseEntity<List<BreedResponse>> findAll() {
        List<BreedResponse> response = StreamSupport.stream(breedService.findAll().spliterator(), false)
                .map(BreedResponse::fromEntity)
                .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BreedResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(BreedResponse.fromEntity(breedService.findByIdOrThrow(id)));
    }

    @GetMapping("/animal-type/{animalTypeId}")
    public ResponseEntity<List<BreedResponse>> findByAnimalType(@PathVariable Long animalTypeId) {
        List<BreedResponse> response = breedService.findAllByAnimalTypeId(animalTypeId).stream()
                .map(BreedResponse::fromEntity)
                .toList();

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<BreedResponse> create(@Valid @RequestBody CreateBreedRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BreedResponse.fromEntity(breedService.save(toBreed(request))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BreedResponse> update(@PathVariable Long id, @Valid @RequestBody UpdateBreedRequest request) {
        breedService.findByIdOrThrow(id);
        return ResponseEntity.ok(BreedResponse.fromEntity(breedService.save(toBreed(id, request))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        breedService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/animal-type/{animalTypeId}/name/{name}")
    public ResponseEntity<BreedResponse> findByNameAndAnimalType(@PathVariable Long animalTypeId, @PathVariable String name) {
        Breed breed = breedService.findByNameIgnoreCaseAndAnimalTypeId(name, animalTypeId)
                .orElseThrow(() -> new NoSuchElementException("Breed not found with name and animal type"));

        return ResponseEntity.ok(BreedResponse.fromEntity(breed));
    }

    private Breed toBreed(CreateBreedRequest request) {
        Breed breed = new Breed();
        breed.setName(request.name());
        breed.setAnimalType(animalTypeService.findByIdOrThrow(request.animalTypeId()));
        return breed;
    }

    private Breed toBreed(Long id, UpdateBreedRequest request) {
        Breed breed = toBreed(new CreateBreedRequest(request.name(), request.animalTypeId()));
        breed.setId(id);
        return breed;
    }
}

