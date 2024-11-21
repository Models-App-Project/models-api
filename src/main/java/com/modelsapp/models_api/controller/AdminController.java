package com.modelsapp.models_api.controller;


import com.modelsapp.models_api.Execptions.ModelException;
import com.modelsapp.models_api.Execptions.UserException;
import com.modelsapp.models_api.entity.Model;
import com.modelsapp.models_api.entity.User;
import com.modelsapp.models_api.service.AdminServices;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

//*************

@RestController
@RequestMapping("/adminUsers")
public class AdminController {

    @Autowired
    private AdminServices adminServices;

    @GetMapping("/getAdminLogged")
    public ResponseEntity<User> obterAdminLogado() {
        User loggedInAdmin = adminServices.getLoggedInAdminUser();
        return ResponseEntity.ok(loggedInAdmin);
    }

    @GetMapping("/getAdmin")
    public ResponseEntity<List<User>> obterTodosAdmins() throws Exception, UserException {
        try {
            List<User> admins = adminServices.getAllAdminUsers();
            return new ResponseEntity<>(admins, HttpStatus.OK);
        } catch (UserException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/getAllModelsRegistred")
    public ResponseEntity<List<Model>> obterTodosModelosRegistrados(@RequestBody Model modelFilters) throws ModelException, Exception {
        try{
            List<Model> allModelsRegistred = adminServices.getAllModels(modelFilters);
            return new ResponseEntity<>(allModelsRegistred, HttpStatus.OK);
        } catch (ModelException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

    @GetMapping("getModelRegistred")
    public ResponseEntity<Model> obterModeloRegistrado(@RequestBody Model modelFilters) throws ModelException, Exception {
        try {
            Model modelRegistred = adminServices.getModel(modelFilters);
            return new ResponseEntity<>(modelRegistred, HttpStatus.OK);
        } catch (ModelException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @PostMapping("/saveAdminUser")
    public ResponseEntity<String> salvarAdmin(@RequestBody User adminUser) {
        try {
            User savedAdmin = adminServices.createAdminUser(adminUser);
            return new ResponseEntity<>("Novo administrador criado " + savedAdmin.getUsername(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao criar administrador." + e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @PutMapping("/updateAdmin")
    public ResponseEntity<String> atualizarAdmin(@RequestBody User adminUser) {
        try {
            User updatedAdmin = adminServices.updateAdminUser(adminUser);
            return new ResponseEntity<>("Administrador atualizado " + updatedAdmin.getUsername(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao atualizar administrador." + e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateModel")
    public ResponseEntity<String> atualizarModelo(@RequestBody Model model) {
        try {
            Model updatedModel = adminServices.updateModel(model);
            return new ResponseEntity<>("Modelo atualizado " + updatedModel.getName(), HttpStatus.OK);
        } catch (ModelException e) {
            return new ResponseEntity<>("Erro ao atualizar modelo." + e.toString(), HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao atualizar modelo." + e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/deleteAdmin")
    public ResponseEntity<String> excluirUsuario(@RequestBody User adminUser) {
        try {
            adminServices.deleteAdminUser(adminUser);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao excluir administrador." + e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
