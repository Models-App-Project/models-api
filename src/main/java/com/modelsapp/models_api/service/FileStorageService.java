package com.modelsapp.models_api.service;


import com.modelsapp.models_api.entity.FileStorage;
import com.modelsapp.models_api.entity.Model;
import com.modelsapp.models_api.entity.User;
import com.modelsapp.models_api.repository.FileStorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FileStorageService {

    @Autowired
    private FileStorageRepository fileStorageRepository;

    private Path fileStorageLocation;


    public List<Resource> getFiles(List<FileStorage> filesRequesters) {
        List<Resource> files = new ArrayList<>();

        filesRequesters.forEach(file -> {
            try {
                String fileName = file.getUploadDir();
                Path fileStorageLocation = Paths.get(fileName).toAbsolutePath().normalize();
                Resource resource = new UrlResource(fileStorageLocation.toUri());
                files.add(resource);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        return files;
    }

    private void addNewUser(FileStorage newStorage, User user) {
        newStorage.setUser(user);
    }

    public FileStorage saveFile(MultipartFile file, String pathName, User user, Model model) {
        try {
            Path filePathTarget = Paths.get(pathName).toAbsolutePath().normalize();
            Files.createDirectories(filePathTarget.getParent());


            FileStorage fileStorage = new FileStorage();
            fileStorage.setUploadDir(pathName);
            if(user != null){
                fileStorage.setUser(user);
            } else if(model != null){
                fileStorage.setModel(model);
            }

            file.transferTo(filePathTarget.toFile());

            Path fileStorageLocation = Paths.get(pathName).toAbsolutePath().normalize();
            Resource resource = new UrlResource(fileStorageLocation.toUri());
            fileStorage.setDownloadURL(resource.getURI().toString());

            fileStorage = fileStorageRepository.save(fileStorage);

            return fileStorage;
        } catch (Exception e) {
            System.out.println(e.toString());
            throw new RuntimeException(e);
        }
    }

    public Resource getFileById(UUID id) {
        FileStorage fileStorage = this.fileStorageRepository.findFileStorageById(id);
        try {
            String fileName = fileStorage.getUploadDir();
            Path filePath = Paths.get(fileName).toAbsolutePath().normalize();
            Resource resource = new UrlResource(filePath.toUri());
            return resource;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void deleteFileById(UUID id) {
        try{
            FileStorage fileStorage = fileStorageRepository.findFileStorageById(id);
            String fileName = fileStorage.getUploadDir();
            Path filePath = Paths.get(fileName).toAbsolutePath().normalize();
            Files.delete(filePath); // Deleta o arquivo

            this.fileStorageRepository.deleteFileStorageById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
