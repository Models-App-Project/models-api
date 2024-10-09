package com.modelsapp.models_api.service;

import com.modelsapp.models_api.model.ModelForm;
import com.modelsapp.models_api.repository.ModelFormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.UUID;
import java.util.List;
import java.util.Optional;

@Service
public class ModelFormService {
    @Autowired
    private ModelFormRepository modelFormRepository;

    // Método para enviar o formulário da modelo
    public Optional<ModelForm> saveModelForm(@Valid @RequestBody ModelForm modelForm) {
        return Optional.of(modelFormRepository.save(modelForm));
    }

    // Método para buscar todos os formulários das modelos
    public List<ModelForm> findAllModelForms() {
        return modelFormRepository.findAll();
    }

    // Método para buscar um formulário da modelo pelo nome
    public Optional<ModelForm> findModelFormByName(String name) {
        return modelFormRepository.findModelFormByName(name);
    }

    // Método para buscar um formulário da modelo pelo ID
    public Optional<ModelForm> findModelFormById(UUID id) {
        return modelFormRepository.findModelFormById(id);
    }

    // Método para deletar um formulário da modelo por ID
    public void deleteModelFormById(UUID id) {
        modelFormRepository.deleteModelFormById(id);
    }

    // Método para deletar um formulário da modelo por nome
    public void deleteModelFormByName(String name) {
        modelFormRepository.findModelFormByName(name).ifPresent(modelForm -> modelFormRepository.delete(modelForm));
    }
}
