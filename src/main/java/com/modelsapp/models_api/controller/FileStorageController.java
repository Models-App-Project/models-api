package com.modelsapp.models_api.controller;

import com.modelsapp.models_api.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/files")
public class FileStorageController {
    @Autowired
    private FileStorageService fileStorageService;

    @GetMapping("/download")
    public ResponseEntity< Resource > downloadFiles(@RequestPart("fileName") String fileName) {
        try {
            Resource resourceToDownload = fileStorageService.getFiles(fileName);

            return ResponseEntity.ok().contentType(MediaType.MULTIPART_FORM_DATA).body(resourceToDownload);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
