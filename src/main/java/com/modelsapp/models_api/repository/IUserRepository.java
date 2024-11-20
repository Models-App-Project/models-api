package com.modelsapp.models_api.repository;


import com.modelsapp.models_api.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.modelsapp.models_api.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<List<User>> getUsersByRoles(Role role);

    Optional<User> getUserById(UUID id);

    void deleteUserById(UUID id);

    //Optional<User> save(User user);


}