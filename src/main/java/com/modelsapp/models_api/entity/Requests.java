package com.modelsapp.models_api.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.NonNull;
import org.hibernate.annotations.Type;


import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "requests")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Requests {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column
    @NonNull
    private boolean status;

    @Column
    @NonNull
    private LocalDateTime requestDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "model_id")
    @JsonBackReference(value = "model-requests")
    private Model model;




}
