package com.modelsapp.models_api.service;

import com.modelsapp.models_api.entity.Role;
import com.modelsapp.models_api.permission.EnumPermission;
import com.modelsapp.models_api.repository.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServices {
    @Autowired
    private IRoleRepository roleRepository;

    public Role findUserByName(EnumPermission name) {
        return roleRepository.findByName(name);
    }
}
