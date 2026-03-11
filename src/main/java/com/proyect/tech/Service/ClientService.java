package com.proyect.tech.Service;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.proyect.tech.DTO.ClientDTO;
import com.proyect.tech.Model.Client;
import com.proyect.tech.Model.Role;
import com.proyect.tech.Model.User;
import com.proyect.tech.Repository.ClientRepository;
import com.proyect.tech.Repository.UserRepository;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ClientService(ClientRepository clientRepository,
                         UserRepository userRepository,
                         PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.userRepository   = userRepository;
        this.passwordEncoder  = passwordEncoder;
    }

    // ── Registro público ──────────────────────────────────
    public ClientDTO register(ClientDTO dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(Role.CLIENTE);
        userRepository.save(user);

        Client client = new Client();
        client.setPhone(dto.getPhone());
        client.setAddress(dto.getAddress());
        client.setUser(user);
        clientRepository.save(client);

        return new ClientDTO(client);
    }

    // ── Listar todos ──────────────────────────────────────
    public List<ClientDTO> getAll() {
        return clientRepository.findAll()
                .stream()
                .map(ClientDTO::new)
                .toList();
    }

    // ── Buscar por ID ─────────────────────────────────────
    public ClientDTO getById(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Cliente no encontrado"));
        return new ClientDTO(client);
    }

    // ── Buscar por email (para /me) ───────────────────────
    public ClientDTO getByEmail(String email) {
        Client client = clientRepository.findByUserEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Cliente no encontrado"));
        return new ClientDTO(client);
    }

    // ── Actualizar ────────────────────────────────────────
    public ClientDTO update(Long id, ClientDTO dto) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Cliente no encontrado"));

        // Actualiza tabla clients
        if (dto.getPhone() != null)   client.setPhone(dto.getPhone());
        if (dto.getAddress() != null) client.setAddress(dto.getAddress());

        // Actualiza tabla users
        User user = client.getUser();
        if (dto.getName() != null)  user.setName(dto.getName());
        if (dto.getEmail() != null) user.setEmail(dto.getEmail());
        userRepository.save(user);  // 👈 guarda cambios en users

        return new ClientDTO(clientRepository.save(client));
    }

    // ── Eliminar ──────────────────────────────────────────
    public void delete(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Cliente no encontrado"));

        clientRepository.delete(client);
      
    }
}