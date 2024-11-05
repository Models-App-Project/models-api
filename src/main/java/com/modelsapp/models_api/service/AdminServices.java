package com.modelsapp.models_api.service;

import com.modelsapp.models_api.Exceptions.ModelException;
import com.modelsapp.models_api.Exceptions.UserException;
import com.modelsapp.models_api.entity.Model;
import com.modelsapp.models_api.entity.User;
import com.modelsapp.models_api.permission.EnumPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AdminServices {

    @Autowired
    private UserService userService;
    @Autowired
    private ModelService modelService;

    //ENCONTRAR=============================================================

    //Retorna os dados do administrador logado
    public User getLoggedInAdminUser() {
        return userService.getloggedInUser();
    }

    //Retorna todos os usuários administradores
    public List<User> getAllAdminUsers() throws Exception {

        try {

            List<User> adminUsers = userService.getUsersByRole(EnumPermission.ADMINISTRADOR);
            if(adminUsers.isEmpty()) {
                throw new UserException("Nenhum usuário administrador encontrado.");
            } else {
                return adminUsers;
            }

        } catch (Exception e) {
            throw new Exception("Erro inesperado ao buscar usuários administradores.", e);
        }
    }

    //Retorna os dados das modelos cadastradas
    public List<Model> getAllModels(Model filters) throws Exception {

        try {

            List<Model> modelsToShow = modelService.findModelsByFilters(filters);
            if(modelsToShow.isEmpty()) {
                throw new ModelException("Nenhuma modelo encontrada.");
            } else {
                return modelsToShow;
            }

        } catch (Exception e) {
            throw new Exception("Erro inesperado ao buscar modelos.", e);
        }
    }

    //Retorna os dados de uma modelo em específico
    public Model getModel(Model filters) throws ModelException {
        try {

            Optional<Model> model = modelService.findModelById(filters.getId());
            if(model.isPresent()) {
                return model.get();
            } else {
                throw new ModelException("Modelo não encontrado");
            }

        } catch (Exception e) {
            throw new ModelException("Erro inesperado ao buscar modelos.", e);
        }
    }

    //=======================================================================


    //CRIAR===============================================================

    //Cria um novo usuário administrador
    public User createAdminUser(User admin) throws UserException {
        try {
            User newAdmin = userService.salvarUsuario(admin);
            return newAdmin;

        } catch (Exception e) {
            throw new UserException("Erro ao criar usuário administrador.", e);
        }

    }

    //=======================================================================

    //ATUALIZAR===============================================================

    public User updateAdminUser(User admin) throws UserException {

        try {

            User updatedAdmin = userService.atualizarUsuario(admin);
            return updatedAdmin;

        } catch (Exception e) {
            throw new UserException("Erro ao atualizar usuário administrador.", e);
        }

    }

    public Model updateModel(Model model) throws ModelException {
        try {
            Model updatedModel = modelService.updateModel(model.getId(), model);
            return updatedModel;
        } catch (Exception e) {
            throw new ModelException("Erro ao atualizar modelo.", e);
        }
    }

    //=======================================================================

    //DELETAR===============================================================

    public void deleteAdminUser(User admin) throws UserException {

        try {

            userService.excluirUsuario(admin);

        } catch (Exception e) {
            throw new UserException("Erro ao deletar usuário administrador.", e);
        }
    }

    public void deleteModel(Model model) throws ModelException {
        try {
            modelService.deleteModelById(model.getId());
        } catch (Exception e) {
            throw new ModelException("Erro ao deletar modelo.", e);
        }
    }

    //=======================================================================
}
