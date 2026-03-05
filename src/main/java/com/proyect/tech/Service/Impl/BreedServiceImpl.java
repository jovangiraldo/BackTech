package com.proyect.tech.Service.Impl;

import com.proyect.tech.Model.Breed;
import com.proyect.tech.Repository.BreedRepository;
import com.proyect.tech.Service.BreedService;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BreedServiceImpl implements BreedService {

    private final BreedRepository breedRepository;

    public BreedServiceImpl(BreedRepository breedRepository) {
        this.breedRepository = breedRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Breed> findAll() {
        return breedRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Breed findByIdOrThrow(Long id) {
        return breedRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Breed not found with id: " + id));
    }

    @Override
    public Breed save(Breed breed) {
        return breedRepository.save(breed);
    }

    @Override
    public void deleteById(Long id) {
        if (!breedRepository.existsById(id)) {
            throw new NoSuchElementException("Breed not found with id: " + id);
        }
        breedRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return breedRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Breed> findAllByAnimalTypeId(Long animalTypeId) {
        return breedRepository.findAllByAnimalTypeId(animalTypeId);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Breed> findByNameIgnoreCaseAndAnimalTypeId(String name, Long animalTypeId) {
        return breedRepository.findByNameIgnoreCaseAndAnimalTypeId(name, animalTypeId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByNameIgnoreCaseAndAnimalTypeId(String name, Long animalTypeId) {
        return breedRepository.existsByNameIgnoreCaseAndAnimalTypeId(name, animalTypeId);
    }
}
