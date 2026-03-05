package com.proyect.tech.Controller;

import com.proyect.tech.DTOs.response.PetResponse;
import com.proyect.tech.DTOs.request.CreatePetRequest;
import com.proyect.tech.DTOs.request.UpdatePetRequest;
import com.proyect.tech.Model.Pet;
import com.proyect.tech.Service.AnimalTypeService;
import com.proyect.tech.Service.BreedService;
import com.proyect.tech.Service.PetService;
import com.proyect.tech.Service.UserService;
import jakarta.validation.Valid;
import java.util.List;
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
@RequestMapping("/pets")
public class PetController {

    private final PetService petService;
    private final UserService userService;
    private final AnimalTypeService animalTypeService;
    private final BreedService breedService;

    public PetController(
            PetService petService,
            UserService userService,
            AnimalTypeService animalTypeService,
            BreedService breedService
    ) {
        this.petService = petService;
        this.userService = userService;
        this.animalTypeService = animalTypeService;
        this.breedService = breedService;
    }

    @GetMapping
    public ResponseEntity<List<PetResponse>> findAll() {
        List<PetResponse> response = StreamSupport.stream(petService.findAll().spliterator(), false)
                .map(PetResponse::fromEntity)
                .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(PetResponse.fromEntity(petService.findByIdOrThrow(id)));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PetResponse>> findByUserId(@PathVariable Long userId) {
        List<PetResponse> response = petService.findAllByUserId(userId).stream()
                .map(PetResponse::fromEntity)
                .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/animal-type/{animalTypeId}")
    public ResponseEntity<List<PetResponse>> findByAnimalTypeId(@PathVariable Long animalTypeId) {
        List<PetResponse> response = petService.findAllByAnimalTypeId(animalTypeId).stream()
                .map(PetResponse::fromEntity)
                .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/breed/{breedId}")
    public ResponseEntity<List<PetResponse>> findByBreedId(@PathVariable Long breedId) {
        List<PetResponse> response = petService.findAllByBreedId(breedId).stream()
                .map(PetResponse::fromEntity)
                .toList();

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<PetResponse> create(@Valid @RequestBody CreatePetRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(PetResponse.fromEntity(petService.save(toPet(request))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PetResponse> update(@PathVariable Long id, @Valid @RequestBody UpdatePetRequest request) {
        petService.findByIdOrThrow(id);
        return ResponseEntity.ok(PetResponse.fromEntity(petService.save(toPet(id, request))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        petService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private Pet toPet(CreatePetRequest request) {
        Pet pet = new Pet();
        pet.setName(request.name());
        pet.setDescription(request.description());
        pet.setUser(userService.findByIdOrThrow(request.userId()));
        pet.setAnimalType(animalTypeService.findByIdOrThrow(request.animalTypeId()));
        pet.setBreed(breedService.findByIdOrThrow(request.breedId()));
        return pet;
    }

    private Pet toPet(Long id, UpdatePetRequest request) {
        Pet pet = toPet(new CreatePetRequest(
                request.name(),
                request.description(),
                request.userId(),
                request.animalTypeId(),
                request.breedId()
        ));
        pet.setId(id);
        return pet;
    }
}

