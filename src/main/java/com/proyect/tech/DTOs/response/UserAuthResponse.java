package com.proyect.tech.DTOs.response;

import com.proyect.tech.Model.UserAuth;

public record UserAuthResponse(
        Long id,
        String email,
        Long userId
) {

    public static UserAuthResponse fromEntity(UserAuth userAuth) {
        Long userId = userAuth.getUser() != null ? userAuth.getUser().getId() : null;

        return new UserAuthResponse(
                userAuth.getId(),
                userAuth.getEmail(),
                userId
        );
    }
}
