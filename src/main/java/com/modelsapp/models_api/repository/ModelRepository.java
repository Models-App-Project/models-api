package com.modelsapp.models_api.repository;

import com.modelsapp.models_api.model.Model;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModelRepository extends JpaRepository<Model, Long> {
    
}
