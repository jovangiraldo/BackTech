package com.proyect.tech.Service.Impl;

import com.proyect.tech.Model.User;
import com.proyect.tech.Repository.UserRepository;
import com.proyect.tech.Service.UserService;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public User findByIdOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found with id: " + id));
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NoSuchElementException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByIdCard(String idCard) {
        return userRepository.findByIdCard(idCard);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByIdCard(String idCard) {
        return userRepository.existsByIdCard(idCard);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByAuthEmail(String email) {
        return userRepository.findByAuthEmail(email);
    }
}
