package com.modelsapp.models_api.controller;

import com.modelsapp.models_api.model.Model;
import com.modelsapp.models_api.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/models")
public class ModelController {
    @Autowired
    private ModelService modelService;

    @GetMapping
    public List<Model> getAllModels() {
        return modelService.findAll();
    }

    @PostMapping
    public Model createModel(@RequestBody Model model) {
        return modelService.save(model);
    }

    @DeleteMapping("/{id}")
    public void deleteModel(@PathVariable Long id) {
        modelService.deleteById(id);
    }
}
