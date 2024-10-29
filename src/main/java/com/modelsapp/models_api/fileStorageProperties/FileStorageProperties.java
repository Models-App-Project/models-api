package com.modelsapp.models_api.fileStorageProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "file")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileStorageProperties {
    private String uploadDir;
}