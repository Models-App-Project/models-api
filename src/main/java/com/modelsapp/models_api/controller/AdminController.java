package com.modelsapp.models_api.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.modelsapp.models_api.Exceptions.ModelException;
import com.modelsapp.models_api.Exceptions.UserException;
import com.modelsapp.models_api.entity.Model;
import com.modelsapp.models_api.entity.User;
import com.modelsapp.models_api.service.AdminServices;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

//*************

@RestController
@RequestMapping("/adminUsers")
public class AdminController {

    @Autowired
    private AdminServices adminServices;

    @GetMapping("/getAdmin")
    public ResponseEntity< List<User> > obterTodosAdmins() throws Exception, UserException {
        try{
            List<User> users = adminServices.getAllAdminUsers();
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
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


    @PostMapping(value = "/saveAdminUser", consumes = "multipart/form-data")
    public ResponseEntity<String> salvarAdmin(
            @RequestPart("adminUser") String adminUserJson,
            @RequestPart("photos") List<MultipartFile> photos,
            @RequestParam("role") String role
    ) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            User adminUser = objectMapper.readValue(adminUserJson, User.class);

            // Processe o adminUser normalmente
            User savedAdmin = adminServices.createAdminUser(adminUser, photos, role);
            return new ResponseEntity<>("Novo administrador criado: " + savedAdmin.getUsername(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao criar administrador: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @PutMapping("/updateAdmin")
    public ResponseEntity<String> atualizarAdmin( @RequestPart("adminUser") String adminUserJson,
                                                  @RequestPart("photos") List<MultipartFile> userPhotos) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            User adminUser = objectMapper.readValue(adminUserJson, User.class);


            User updatedAdmin = adminServices.updateAdminUser(adminUser, userPhotos);
            return new ResponseEntity<>("Administrador atualizado " + updatedAdmin.getUsername(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao atualizar administrador." + e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateModel")
    public ResponseEntity<String> atualizarModelo( @RequestPart("adminUser") String modelJson,
                                                   @RequestPart("photos") List<MultipartFile> photos
                                                 ) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Model convertedModel = objectMapper.readValue(modelJson, Model.class);

            Model updatedModel = adminServices.updateModel(convertedModel, photos);
            return new ResponseEntity<>("Modelo atualizado " + updatedModel.getName(), HttpStatus.OK);
        } catch (ModelException e) {
            return new ResponseEntity<>("Erro ao atualizar modelo." + e.toString(), HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao atualizar modelo." + e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/deleteAdmin")
    public ResponseEntity<String> excluirUsuario(@RequestParam UUID adminUserID) {
        try {
            adminServices.deleteAdminUser(adminUserID);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao excluir administrador." + e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
