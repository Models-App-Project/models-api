package com.modelsapp.models_api.service;

import com.modelsapp.models_api.model.Model;
import com.modelsapp.models_api.repository.ModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.List;
import java.util.Optional;

@Service
public class ModelService {
    @Autowired
    private ModelRepository modelRepository;

    // Método para buscar todas as modelos
    public List<Model> findAll() {
        return modelRepository.findAll();
    }

    // Método para salvar uma nova modelo
    public Model saveModel(Model model) {
        return modelRepository.save(model);
    }

    // Método para buscar uma modelo por ID
    public Optional<Model> findById(UUID id) {
        return modelRepository.findById(id);
    }

    // Método para buscar uma modelo por nome
    public Optional<Model> findByName(String name) {
        return modelRepository.findByName(name);
    }
}
