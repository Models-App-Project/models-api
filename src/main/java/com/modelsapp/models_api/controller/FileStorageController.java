package com.modelsapp.models_api.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;

import com.modelsapp.models_api.service.FileStorageService;

import jakarta.annotation.*;


@Controller
@RequestMapping("/api/file")
public class FileStorageController {
    @Autowired
    private FileStorageService fileStorageService;

    @GetMapping("/download")
    public ResponseEntity< Resource > downloadFiles(@RequestPart("fileName") String fileName) {
        try {
            Resource resourceToDownload = fileStorageService.getFiles(fileName);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
