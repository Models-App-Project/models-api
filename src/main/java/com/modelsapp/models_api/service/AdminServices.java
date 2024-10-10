package com.modelsapp.models_api.service;

import com.modelsapp.models_api.Execptions.ModelException;
import com.modelsapp.models_api.entity.Admin;
import com.modelsapp.models_api.entity.Model;
import com.modelsapp.models_api.entity.Role;
import com.modelsapp.models_api.entity.User;
import com.modelsapp.models_api.permission.EnumPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class AdminServices {

    @Autowired
    private UserService userService;
    @Autowired
    private ModelService modelService;


    private User toUser(Admin admin) {
        User user = new User();
        user.setId(admin.getId());
        user.setUsername(admin.getUsername());
        user.setPassword(admin.getPassword());
        user.setUsername(admin.getUsername());
        user.setRoles(admin.getRoles());
        return user;
    }


    //Retorna os dados do administrador logado
    public User getLoggedInAdminUser(Admin admin) {
        User loggedAdmin = userService.getloggedInUser();

    }


    //Retorna todos os usuários administradores
    public List<User> getAllAdminUsers(User loggedInAdmin) {
        List<User> adminUsers = userService.getUsersByRoles("ADMINISTRADOR");

        return  adminUsers.stream().filter(user -> !user.equals(loggedInAdmin))
                .toList();
    }

    //Retorna as modelos para realizar um gerenciamento
    public List<Model> getAllModels(Model filters) throws ModelException {

        try {
            Role roleFilter = new Role();
            roleFilter.setName(EnumPermission.ADMINISTRADOR);
            boolean adminLoged = userService.isUserLoggedIn(roleFilter);
            if(adminLoged){
                List<Model> modelToShow = modelService.findModelsByFilters(filters);
                return modelToShow;
            }
            else {
                throw new ModelException("Não existe usuário logado ou usuário não é administrador.");
            }

        } catch (Exception e) {
            throw new ModelException("Erro ao buscar modelos.", e);
        }
    }

    //Retorna os dados de uma modelo em específico
    public Model getModell(Model filters)  throws ModelException {
        try {
            Role roleFilter = new Role();
            roleFilter.setName(EnumPermission.ADMINISTRADOR);
            boolean adminLoged = userService.isUserLoggedIn(roleFilter);
            if(adminLoged){
                Optional<Model> model = modelService.findModelById(filters.getId());
                Model modelToShow = model.get();
                return modelToShow;
            }
            else {
                throw new ModelException("Não existe usuário logado ou usuário não é administrador.");
            }

        } catch (Exception e) {
            throw new ModelException("Erro ao buscar modelos.", e);
        }
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
