package com.modelsapp.models_api.repository;

import com.modelsapp.models_api.entity.FileStorage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FileStorageRepository extends JpaRepository<FileStorage, UUID> {
    // Método para buscar um arquivo pelo ID
    public FileStorage findFileStorageById(UUID id);

    // Método para deletar um arquivo por ID
    public void deleteFileStorageById(UUID id);

}
