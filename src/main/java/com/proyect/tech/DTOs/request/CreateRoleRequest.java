package com.proyect.tech.DTOs.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CreateRoleRequest(
        @NotBlank(message = "Role name is required")
        @Pattern(regexp = "ROLE_[A-Z]+", message = "Role name must start with 'ROLE_' followed by uppercase letters (e.g. ROLE_ADMIN)")
        String name
) {
    public com.proyect.tech.Model.Role toEntity() {
        com.proyect.tech.Model.Role role = new com.proyect.tech.Model.Role();
        role.setName(name);
        return role;
    }
}
