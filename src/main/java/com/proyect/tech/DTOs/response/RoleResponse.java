package com.proyect.tech.DTOs.response;

import com.proyect.tech.Model.Role;

public record RoleResponse(
        Long id,
        String name
) {
    public static RoleResponse fromEntity(Role role) {
        return new RoleResponse(
                role.getId(),
                role.getName()
        );
    }
}
