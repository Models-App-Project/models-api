package com.modelsapp.models_api.repository;

import com.modelsapp.models_api.entity.ModelForm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ModelFormRepository extends JpaRepository<ModelForm, UUID> {
    // Método para buscar um formulário da modelo pelo nome
    public Optional<ModelForm> findModelFormByName(String name);

    // Método para buscar um formulário da modelo pelo ID
    public Optional<ModelForm> findModelFormById(UUID id);

    // Método para deletar um formulário da modelo por ID
    public void deleteModelFormById(UUID id);

    // Método para deletar um formulário da modelo por nome
    public void deleteModelFormByName(String name);
}
