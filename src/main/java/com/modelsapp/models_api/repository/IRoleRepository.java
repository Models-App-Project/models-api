package com.modelsapp.models_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.modelsapp.models_api.entity.Role;
import com.modelsapp.models_api.permission.EnumPermission;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {
    Role findByName(EnumPermission name);

    Role save(Role role);
}