package com.modelsapp.models_api.fileStorageProperties;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;


@Configuration
@ConfigurationProperties(prefix = "file")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class FileStorageProperties {
    @Column(nullable = false)
    private String uploadDir;

    @Column(nullable = false)
    private String downloadURL;
}

