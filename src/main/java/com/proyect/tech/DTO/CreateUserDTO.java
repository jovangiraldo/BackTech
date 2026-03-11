// CreateUserDTO.java
package com.proyect.tech.DTO;

import com.proyect.tech.Model.Role;

public class CreateUserDTO {
    private String name;
    private String email;
    private String password;
    private Role role;  

    public String getName()     { return name; }
    public String getEmail()    { return email; }
    public String getPassword() { return password; }
    public Role getRole()       { return role; }

    public void setName(String name)       { this.name = name; }
    public void setEmail(String email)     { this.email = email; }
    public void setPassword(String pass)   { this.password = pass; }
    public void setRole(Role role)         { this.role = role; }
}