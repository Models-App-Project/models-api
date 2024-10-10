package com.modelsapp.models_api.service;

import com.modelsapp.models_api.entity.Admin;
import com.modelsapp.models_api.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.modelsapp.models_api.service.UserService;

import java.util.List;

@Service
@Transactional
public class AdminServices {

    @Autowired
    private UserService userService;

    public List<User> getAllAdminUsers() {
        return userService.getUsersByRoles("ADMINISTRADOR");
    }

    public User loggedInAdminUser(Admin admin) {
       /* return userService.obterUsuarios().stream()
                .filter(user -> user.getEmail().equals(admin.getEmail()))
                .filter(user -> user.getPassword().equals(admin.getPassword()))
                .filter(user -> user.getRoles().stream()
                        .anyMatch(role -> "ADMINISTRADOR".equals(role.getName())))
                .findFirst()
                .orElse(null);*/
        return userService.getUsersByRoles("ADMINISTRADOR").stream()
                .filter(user -> user.getEmail().equals(admin.getEmail()))
                .filter(user -> user.getPassword().equals(admin.getPassword()))
                .findFirst()
                .orElse(null);
    }

    private User toUser(Admin admin) {
        User user = new User();
        user.setId(admin.getId());
        user.setUsername(admin.getUsername());
        user.setPassword(admin.getPassword());
        user.setUsername(admin.getUsername());
        user.setRoles(admin.getRoles());
        return user;
    }

    public User createAdminUser(Admin admin) {
        boolean loggedInUser = userService.isUserLoggedIn(this.toUser(admin));

        if (loggedInUser != null) {
            User newUser = new User();
            newUser.setUsername(admin.getEmail());
            newUser.setPassword(admin.getPassword());
            newUser.setRoles(List.of(new Role("ADMINISTRADOR")));
            return userService.salvarUsuario(newUser);
        }
        return null;
    }
}
