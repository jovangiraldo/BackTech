package com.proyect.tech.Service.Impl;

import com.proyect.tech.Model.UserAuth;
import com.proyect.tech.Repository.UserAuthRepository;
import com.proyect.tech.Service.UserAuthService;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserAuthServiceImpl implements UserAuthService {

    private final UserAuthRepository userAuthRepository;

    public UserAuthServiceImpl(UserAuthRepository userAuthRepository) {
        this.userAuthRepository = userAuthRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<UserAuth> findAll() {
        return userAuthRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public UserAuth findByIdOrThrow(Long id) {
        return userAuthRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("UserAuth not found with id: " + id));
    }

    @Override
    public UserAuth save(UserAuth userAuth) {
        return userAuthRepository.save(userAuth);
    }

    @Override
    public void deleteById(Long id) {
        if (!userAuthRepository.existsById(id)) {
            throw new NoSuchElementException("UserAuth not found with id: " + id);
        }
        userAuthRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return userAuthRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserAuth> findByEmail(String email) {
        return userAuthRepository.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return userAuthRepository.existsByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserAuth> findByUserId(Long userId) {
        return userAuthRepository.findByUserId(userId);
    }
}
