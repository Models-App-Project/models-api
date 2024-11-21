package com.modelsapp.models_api.service;

import com.modelsapp.models_api.entity.Role;
import com.modelsapp.models_api.entity.User;
import com.modelsapp.models_api.permission.EnumPermission;
import com.modelsapp.models_api.repository.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class RoleServices {
    @Autowired
    private IRoleRepository roleRepository;

    public Role findUserByName(EnumPermission name) throws IllegalArgumentException {
        return roleRepository.findByName(name);
    }

    public Role getNewRoleByString(String roleName) {

        switch (roleName) {
            case "ADMINISTRADOR": {
                Role newRole = new Role();
                newRole.setName(EnumPermission.ADMINISTRADOR);
                return newRole;
            }
            case "USUARIO": {
                Role newRole = new Role();
                newRole.setName(EnumPermission.ADMINISTRADOR);
                return newRole;
            }
            default:
                throw new IllegalArgumentException("Role not found");
        }
    }

    public void  addNewUser(Role newRole, User user) {
        if(newRole.getUsers() == null) {
            newRole.setUsers(new ArrayList<>());
        }
        newRole.getUsers().add(user);
    }

    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }
}
