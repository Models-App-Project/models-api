package com.modelsapp.models_api.repository;

import com.modelsapp.models_api.model.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;


@Repository
public interface ModelRepository extends JpaRepository<Model, Long> {
    public Optional<Model> findById(Long id);

}
