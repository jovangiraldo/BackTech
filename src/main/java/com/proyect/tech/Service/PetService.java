package com.proyect.tech.Service;

import com.proyect.tech.DTO.PetDTO;
import com.proyect.tech.Model.Client;
import com.proyect.tech.Model.Pet;
import com.proyect.tech.Repository.ClientRepository;
import com.proyect.tech.Repository.PetRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class PetService {

    private final PetRepository petRepository;
    private final ClientRepository clientRepository;

    public PetService(PetRepository petRepository,
            ClientRepository clientRepository) {
        this.petRepository = petRepository;
        this.clientRepository = clientRepository;
    }

    // ── Listar todas ──────────────────────────────────────
    public List<PetDTO> getAll() {
        return petRepository.findAll()
                .stream()
                .map(PetDTO::new)
                .toList();
    }

    // ── Listar por cliente ────────────────────────────────
    public List<PetDTO> getByClient(Long clientId) {
        return petRepository.findByClientId(clientId)
                .stream()
                .map(PetDTO::new)
                .toList();
    }

    // ── Buscar por ID ─────────────────────────────────────
    public PetDTO getById(Long id) {
        return petRepository.findById(id)
                .map(PetDTO::new)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Mascota no encontrada"));
    }

    // ── Crear ─────────────────────────────────────────────
    public PetDTO create(PetDTO dto) {
        Client client = clientRepository.findById(dto.getClientId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Cliente no encontrado"));

        Pet pet = new Pet();
        pet.setName(dto.getName());
        pet.setType(dto.getType());
        pet.setYearOld(dto.getYearOld());
        pet.setObservation(dto.getObservation());
        pet.setIdentifier(dto.getIdentifier());
        pet.setClient(client); // 👈 vincula al cliente

        return new PetDTO(petRepository.save(pet));
    }

    // ── Actualizar ────────────────────────────────────────
    public PetDTO update(Long id, PetDTO dto) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Mascota no encontrada"));

        if (dto.getName() != null)
            pet.setName(dto.getName());
        if (dto.getType() != null)
            pet.setType(dto.getType());
        if (dto.getYearOld() != null)
            pet.setYearOld(dto.getYearOld());
        if (dto.getObservation() != null)
            pet.setObservation(dto.getObservation());
        if (dto.getIdentifier() != null)
            pet.setIdentifier(dto.getIdentifier());

        // Cambiar de dueño si viene clientId diferente
        if (dto.getClientId() != null) {
            Client client = clientRepository.findById(dto.getClientId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Cliente no encontrado"));
            pet.setClient(client);
        }

        return new PetDTO(petRepository.save(pet));
    }

    // ── Eliminar ──────────────────────────────────────────
    public void delete(Long id) {
        if (!petRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Mascota no encontrada");
        }
        petRepository.deleteById(id);
    }

    public PetDTO getByIdentifier(String identifier) {
        return petRepository.findByIdentifier(identifier)
                .map(PetDTO::new)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Mascota no encontrada"));
    }
}