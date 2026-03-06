package com.proyect.tech.Service;

import com.proyect.tech.Model.Role;
import java.util.List;

public interface RoleService {

    List<Role> findAll();

    Role findByIdOrThrow(Long id);

    Role save(Role role);

    void deleteById(Long id);
}
