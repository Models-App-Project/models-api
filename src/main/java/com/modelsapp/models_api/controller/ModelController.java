package com.modelsapp.models_api.controller;

import com.modelsapp.models_api.entity.Model;
import com.modelsapp.models_api.service.ModelService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;
import java.util.Optional;
import java.util.List;

@RestController
@RequestMapping("/models")
public class ModelController {
    @Autowired
    private ModelService modelService;

    // Endpoint para cadastrar uma nova modelo
    @PostMapping("/add")
    public ResponseEntity<Model> addModel(@RequestBody Model model) {
        Model savedModel = modelService.saveModel(model);
        return ResponseEntity.ok(savedModel);
    }

    // Endpoint para buscar uma modelo por ID
    @GetMapping("/{id}") 
    public ResponseEntity<Model> getModelById(@PathVariable UUID id) {
        Optional<Model> model = modelService.findModelById(id);
        return model.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint para buscar todas as modelos
    @GetMapping("/findAll")
    public ResponseEntity<List<Model>> getAllModels() {
        List<Model> models = modelService.findAllModels();
        return ResponseEntity.ok(models);
    }

    // Endpoint para buscar uma modelo por nome
    @GetMapping("/findByName")
    public ResponseEntity<Model> getModelByName(@RequestParam String name) {
        Optional<Model> model = modelService.findModelByName(name);
        return model.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
