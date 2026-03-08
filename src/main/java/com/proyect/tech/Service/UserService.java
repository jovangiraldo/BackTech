package com.proyect.tech.Service;

import com.proyect.tech.Model.User;
import com.proyect.tech.DTO.LoginRequest;
import com.proyect.tech.DTO.LoginResponse;
import com.proyect.tech.DTO.UserDTO;

import java.util.List;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.proyect.tech.Repository.UserRepository;
import com.proyect.tech.Exception.ResourceNotFoundException;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }  

    public User crearUsuario(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public User update(long id, User userDetails) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        if (userDetails.getName() != null &&
                !userDetails.getName().trim().isEmpty()) {
            user.setName(userDetails.getName());
        }
        if (userDetails.getEmail() != null &&
                !userDetails.getEmail().trim().isEmpty()) {
            user.setEmail(userDetails.getEmail());
        }
        if (userDetails.getPassword() != null &&
                !userDetails.getPassword().trim().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        }

        if (userDetails.getRole() != null)
            user.setRole(userDetails.getRole());

        return userRepository.save(user);
    }
    

    public LoginResponse login(LoginRequest request) {
       
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
       
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ResourceNotFoundException("Contraseña incorrecta");
        }   
        String token = jwtService.generateToken(user);
        UserDTO userDTO = new UserDTO(user);

        return new LoginResponse(token, userDTO);
    }
}