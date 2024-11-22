package com.modelsapp.models_api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.modelsapp.models_api.Execptions.ModelException;
import com.modelsapp.models_api.entity.Model;
import com.modelsapp.models_api.entity.ModelForm;
import com.modelsapp.models_api.service.ModelFormService;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

@RestController
@RequestMapping("/modelsforms")
public class ModelFormController {
    @Autowired
    private ModelFormService modelFormService;

    // Endpoint para enviar um formulário da modelo
    @PostMapping("/sendForm")
    public ResponseEntity<Model> sendModelForm( @RequestPart("model") String model,
                                                    @RequestPart("photos") List<String> photos
                                                    ) throws JsonMappingException, JsonProcessingException, ModelException {
            ObjectMapper objectMapper = new ObjectMapper();
            Model convertdModel = objectMapper.readValue(model, Model.class);

            Model savedModelForm = modelFormService.saveModel(convertdModel, photos);

            if (savedModelForm.getId() != null) {
                return new ResponseEntity<>(savedModelForm, HttpStatus.CREATED);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
    }

    // Endpoint para buscar todos os formulários das modelos
    @GetMapping("/findAllForms")
    public ResponseEntity<List<ModelForm>> getAllModelForms() {
        List<ModelForm> modelForms = modelFormService.findAllModelForms();
        return ResponseEntity.ok(modelForms);
    }

    // Endpoint para buscar um formulário da modelo pelo nome
    @GetMapping("/findByName")
    public ResponseEntity<ModelForm> getModelFormByName(@RequestParam String name) {
        Optional<ModelForm> modelForm = modelFormService.findModelFormByName(name);
        return modelForm.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint para buscar um formulário da modelo pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<ModelForm> getModelFormById(@PathVariable UUID id) {
        Optional<ModelForm> modelForm = modelFormService.findModelFormById(id);
        return modelForm.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint para deletar um formulário da modelo por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteModelFormById(@PathVariable UUID id) {
        modelFormService.deleteModelFormById(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint para deletar um formulário da modelo por nome
    @DeleteMapping("/deleteByName")
    public ResponseEntity<Void> deleteModelFormByName(@RequestParam String name) {
        modelFormService.deleteModelFormByName(name);
        return ResponseEntity.noContent().build();
    }
}
