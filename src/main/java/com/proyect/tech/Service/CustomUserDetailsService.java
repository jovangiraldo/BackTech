package com.proyect.tech.Service;

import com.proyect.tech.Model.UserAuth;
import com.proyect.tech.Repository.UserAuthRepository;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CustomUserDetailsService implements UserDetailsService {

    private final UserAuthRepository userAuthRepository;

    public CustomUserDetailsService(UserAuthRepository userAuthRepository) {
        this.userAuthRepository = userAuthRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserAuth userAuth = userAuthRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        String passwordHash = userAuth.getPasswordHash();
        if (passwordHash == null || passwordHash.isBlank()) {
            throw new NoSuchElementException("User password is not configured");
        }

        Set<SimpleGrantedAuthority> authorities = userAuth.getUser() != null && userAuth.getUser().getRoles() != null
                ? userAuth.getUser().getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName()))
                        .collect(Collectors.toSet())
                : Set.of(new SimpleGrantedAuthority("ROLE_USER"));

        return User.builder()
                .username(userAuth.getEmail())
                .password(passwordHash)
                .authorities(authorities)
                .build();
    }
}
