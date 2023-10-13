package com.denismerenkov.service.impl;

import com.denismerenkov.model.user.Role;
import com.denismerenkov.repository.RoleRepository;
import com.denismerenkov.service.RoleService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    private RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void add(Role role){
        try {
            this.roleRepository.save(role);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Role is already exists!");
        }
    }
}
