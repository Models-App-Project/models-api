package com.modelsapp.models_api.controller;

import com.modelsapp.models_api.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.modelsapp.models_api.fileStorageProperties.FileStorageProperties;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/files")
public class FileStorageController {
    @Autowired
    private FileStorageService fileStorageService;
  
    @GetMapping("/download")
    public ResponseEntity< Resource > downloadFiles(@RequestPart("fileName") String fileName) {
    // Endpoint para fazer o upload de um arquivo
    @PostMapping("/upload")
    @PreAuthorize("hasRole(T(com.modelsapp.models_api.permission.EnumPermission).ADMINISTRADOR.toString(), T(com.modelsapp.models_api.permission.EnumPermission).SUB_ADMINISTRADOR.toString())")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        @SuppressWarnings("null")
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            Resource resourceToDownload = fileStorageService.getFiles(fileName);

            return ResponseEntity.ok().contentType(MediaType.MULTIPART_FORM_DATA).body(resourceToDownload);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // Endpoint para listar os arquivos
    @GetMapping("/list")
    public ResponseEntity<List<String>> listFiles() throws IOException {
        List<String> filesNames = Files.list(fileStorageLocation)
        .map(Path::getFileName)
        .map(Path::toString)
        .collect(Collectors.toList());

        return ResponseEntity.ok().body(filesNames);
    }

    // Endpoint para deletar um arquivo
    @DeleteMapping("/delete/{fileName:.+}")
    @PreAuthorize("hasRole(T(com.modelsapp.models_api.permission.EnumPermission).ADMINISTRADOR.toString(), T(com.modelsapp.models_api.permission.EnumPermission).SUB_ADMINISTRADOR.toString())")
    public ResponseEntity<String> deleteFile(@PathVariable String fileName) {
        try {
            Path filePath = fileStorageLocation.resolve(fileName).normalize();
            Files.delete(filePath); // Deleta o arquivo

            return ResponseEntity.ok("File deleted successfully: " + fileName);
        } catch (IOException ex) {
            return ResponseEntity.status(500).body("Could not delete file: " + fileName);
        }
    }
}
