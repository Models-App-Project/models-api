package com.modelsapp.models_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.modelsapp.models_api.entity.Model;
import com.modelsapp.models_api.entity.ModelForm;
import com.modelsapp.models_api.entity.User;
import com.modelsapp.models_api.service.EmailService;
import com.modelsapp.models_api.service.ModelFormService;

import com.modelsapp.models_api.service.ModelService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

@RestController
@RequestMapping("/modelsforms")
public class ModelFormController {
    @Autowired
    private ModelService modelFormService;

    // Endpoint para enviar um formulário da modelo
    @PostMapping("/sendForm")
    public ResponseEntity<Model> sendModelForm( @RequestPart("model") String model,
                                                    @RequestPart("photos") List<String> photos
                                                    ) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Model convertdModel = objectMapper.readValue(model, Model.class);

            Model savedModelForm = modelFormService.saveModel(convertdModel, photos);

            if (savedModelForm.getId() != null) {

                String assunto = "Confirmação de envio de formulário";
                String mensagem = "Seu formulário foi enviado com sucesso!\n\nObrigado por entrar em contato. Em breve retornaremos. \n\nAtenciosamente, \nEquipe ModelsApp";
                return new ResponseEntity<>(savedModelForm, HttpStatus.CREATED);
            } else {
                throw new Exception("Modelo não encontrado.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    // Endpoint para buscar todos os formulários das modelos
    @GetMapping("/findAllForms")
    public ResponseEntity<List<Model>> getAllModelForms() {
        try {
            List<Model> modelForms = modelFormService.findAllModels();
            return new ResponseEntity<>(modelForms, HttpStatus.FOUND);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Endpoint para buscar um formulário da modelo pelo nome
    @GetMapping("/findByName")
    public ResponseEntity<Model> getModelFormByName(@RequestParam String name) {
        try {
            Optional<Model> modelForm = modelFormService.findModelByName(name);
            return new ResponseEntity<>(modelForm.get(), HttpStatus.FOUND);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Endpoint para buscar um formulário da modelo pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<Model> getModelFormById(@PathVariable UUID id) {
        try {
            Optional<Model> modelForm = modelFormService.findModelById(id);
            return new ResponseEntity<>(modelForm.get(), HttpStatus.FOUND);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Endpoint para deletar um formulário da modelo por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteModelFormById(@PathVariable UUID id) {
        try {
            modelFormService.deleteModelById(id);
            return new ResponseEntity<>("Exclusão realizada com sucesso", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao tentar excluir o formulário da modelo.\n" + e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint para deletar um formulário da modelo por nome
    @DeleteMapping("/deleteByName")
    public ResponseEntity<String> deleteModelFormByName(@RequestParam String name) {
        try {
            modelFormService.deleteModelByName(name);
            return new ResponseEntity<>("Exclusão realizada com sucesso", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao tentar excluir o formulário da modelo.\n" + e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
