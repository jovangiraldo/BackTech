package com.proyect.tech.Service.Impl;

import com.proyect.tech.Model.Pet;
import com.proyect.tech.Repository.PetRepository;
import com.proyect.tech.Service.PetService;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;

    public PetServiceImpl(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Pet> findAll() {
        return petRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Pet findByIdOrThrow(Long id) {
        return petRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Pet not found with id: " + id));
    }

    @Override
    public Pet save(Pet pet) {
        return petRepository.save(pet);
    }

    @Override
    public void deleteById(Long id) {
        if (!petRepository.existsById(id)) {
            throw new NoSuchElementException("Pet not found with id: " + id);
        }
        petRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return petRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pet> findAllByUserId(Long userId) {
        return petRepository.findAllByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pet> findAllByAnimalTypeId(Long animalTypeId) {
        return petRepository.findAllByAnimalTypeId(animalTypeId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pet> findAllByBreedId(Long breedId) {
        return petRepository.findAllByBreedId(breedId);
    }
}
