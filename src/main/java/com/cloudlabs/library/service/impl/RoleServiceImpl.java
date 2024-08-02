package com.cloudlabs.library.service.impl;

import com.cloudlabs.library.enums.RoleEnum;
import com.cloudlabs.library.model.Role;
import com.cloudlabs.library.repository.RoleRepository;
import com.cloudlabs.library.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {


    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role readByName(RoleEnum name) {
        return roleRepository.findByName(name);
    }
}
