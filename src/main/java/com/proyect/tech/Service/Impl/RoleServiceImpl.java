package com.proyect.tech.Service.Impl;

import com.proyect.tech.Model.Role;
import com.proyect.tech.Repository.RoleRepository;
import com.proyect.tech.Service.RoleService;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Role findByIdOrThrow(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Role not found with id: " + id));
    }

    @Override
    public Role save(Role role) {
        if (roleRepository.existsByName(role.getName())) {
            throw new IllegalStateException("Role already exists with name: " + role.getName());
        }
        return roleRepository.save(role);
    }

    @Override
    public void deleteById(Long id) {
        if (!roleRepository.existsById(id)) {
            throw new NoSuchElementException("Role not found with id: " + id);
        }
        roleRepository.deleteById(id);
    }
}
