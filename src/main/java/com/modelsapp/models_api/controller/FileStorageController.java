package com.modelsapp.models_api.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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


@Controller
@RequestMapping("/api/file")
public class FileStorageController {
    private final Path fileStorageLocation;

    // Construtor da classe
    public FileStorageController(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();
    }

    // Endpoint para fazer o upload de um arquivo
    @PostMapping("/upload")
    @PreAuthorize("hasRole(T(com.modelsapp.models_api.permission.EnumPermission).ADMINISTRADOR.toString(), T(com.modelsapp.models_api.permission.EnumPermission).SUB_ADMINISTRADOR.toString())")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        @SuppressWarnings("null")
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        
        try {
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            file.transferTo(targetLocation.toFile());

            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/file/download/")
                    .path(fileName)
                    .toUriString();

            return ResponseEntity.ok("File uploaded successfully Download URL: " + fileDownloadUri);
            
        } catch (IOException ex) {
            return ResponseEntity.badRequest().body("Could not store file " + fileName + ". Please try again!");
        }
    }

    // Endpoint para fazer o download de um arquivo
    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) throws IOException {
        Path filePath = fileStorageLocation.resolve(fileName).normalize();

        try {
            Resource resource = new UrlResource(filePath.toUri());
            String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
            
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
            
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
