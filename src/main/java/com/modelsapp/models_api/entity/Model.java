package com.modelsapp.models_api.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.UUID;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Model {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull
    private String name;

    @NotNull
    @Min(value=0,message="Digite uma idade valida, nao pode ser menor que 0")
    @Max(value=120,message="Digite uma idade valida")
    private int age;

    private String description;

    @NotNull
    private String eyesColor;

    @NotNull
    private String hairColor;

    @NotNull
    @Min(value=0,message="Digite uma valor valido, nao pode ser menor que 0")
    private double height;

    @NotNull
    @Min(value=0,message="Digite uma valor valido, nao pode ser menor que 0")
    private double weight;

    @NotNull
    @Min(value=0,message="Digite uma valor valido, nao pode ser menor que 0")
    private double waistline;

    @NotNull
    @Min(value=0,message="Digite uma valor valido, nao pode ser menor que 0")
    private double hip;

    @NotNull
    @Min(value=0,message="Digite uma valor valido, nao pode ser menor que 0")
    private double bust;

    @OneToOne(mappedBy = "model", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference("model-requests")
    private Requests request;


    @OneToMany(mappedBy = "model", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference("model-files")
    private List<FileStorage> photos;



}