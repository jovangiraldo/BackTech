package com.proyect.tech.Service;

import com.proyect.tech.Model.UserAuth;
import java.util.Optional;

public interface UserAuthService {

    Iterable<UserAuth> findAll();

    UserAuth findByIdOrThrow(Long id);

    UserAuth save(UserAuth userAuth);

    void deleteById(Long id);

    boolean existsById(Long id);

    Optional<UserAuth> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<UserAuth> findByUserId(Long userId);
}
