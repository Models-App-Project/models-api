package com.modelsapp.models_api.service;

import com.modelsapp.models_api.model.Model;
import com.modelsapp.models_api.repository.ModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ModelService {
    @Autowired
    private ModelRepository modelRepository;

    public List<Model> findAll() {
        return modelRepository.findAll();
    }

    public Model saveModel(Model model) {
        return modelRepository.save(model);
    }

    public Optional<Model> findById(Long id) {
        return modelRepository.findById(id);
    }
}
