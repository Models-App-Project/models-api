package com.modelsapp.models_api.controller;

import com.modelsapp.models_api.model.Model;
import com.modelsapp.models_api.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
    public ResponseEntity<Model> getModelById(@PathVariable Long id) {
        Optional<Model> model = modelService.findById(id);
        return model.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
