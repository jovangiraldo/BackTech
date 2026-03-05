package com.proyect.tech.Service.Impl;

import com.proyect.tech.Model.AnimalType;
import com.proyect.tech.Repository.AnimalTypeRepository;
import com.proyect.tech.Service.AnimalTypeService;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AnimalTypeServiceImpl implements AnimalTypeService {

    private final AnimalTypeRepository animalTypeRepository;

    public AnimalTypeServiceImpl(AnimalTypeRepository animalTypeRepository) {
        this.animalTypeRepository = animalTypeRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<AnimalType> findAll() {
        return animalTypeRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public AnimalType findByIdOrThrow(Long id) {
        return animalTypeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("AnimalType not found with id: " + id));
    }

    @Override
    public AnimalType save(AnimalType animalType) {
        return animalTypeRepository.save(animalType);
    }

    @Override
    public void deleteById(Long id) {
        if (!animalTypeRepository.existsById(id)) {
            throw new NoSuchElementException("AnimalType not found with id: " + id);
        }
        animalTypeRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return animalTypeRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AnimalType> findByNameIgnoreCase(String name) {
        return animalTypeRepository.findByNameIgnoreCase(name);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByNameIgnoreCase(String name) {
        return animalTypeRepository.existsByNameIgnoreCase(name);
    }
}
