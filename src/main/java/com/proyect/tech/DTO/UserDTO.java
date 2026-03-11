// UserDTO.java  — lo que devuelves, nunca la entidad directa
package com.proyect.tech.DTO;

import com.proyect.tech.Model.User;
import com.proyect.tech.Model.Role;

public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private Role role;

    public UserDTO(User user) {
        this.id    = user.getId();
        this.name  = user.getName();
        this.email = user.getEmail();
        this.role  = user.getRole();
        // 👆 nunca incluyas password aquí
    }

    public Long getId()    { return id; }
    public String getName()  { return name; }
    public String getEmail() { return email; }
    public Role getRole()    { return role; }
}