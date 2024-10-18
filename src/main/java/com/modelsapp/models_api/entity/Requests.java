package com.modelsapp.models_api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "requests")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Requests {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private UUID id;

    @Column(unique = true, length = 10)
    @NonNull
    private String status;

    @Column(unique = true)
    @NonNull
    private LocalDateTime requestDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "model_id", nullable = false)
    @NonNull
    private Model model;

}
