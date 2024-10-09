package com.modelsapp.models_api.repository;

import com.modelsapp.models_api.entity.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ModelRepository extends JpaRepository<Model, UUID> {
    // Método para buscar uma modelo por ID
    public Optional<Model> findModelById(UUID id);

    // Método para buscar uma modelo por nome
    public Optional<Model> findModelByName(String name);

    // Método para deletar uma modelo por ID
    public void deleteModelById(UUID id);

    // Método para deletar uma modelo por nome
    public void deleteModelByName(String name);
}
