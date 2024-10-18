package com.modelsapp.models_api.repository;

import com.modelsapp.models_api.permission.EnumPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.modelsapp.models_api.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<List<User>> getUsersByRoles(EnumPermission role);
}