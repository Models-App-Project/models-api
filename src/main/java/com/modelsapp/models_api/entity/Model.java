package com.modelsapp.models_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonManagedReference;

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


    public Model(UUID id, @NotNull String name,
            @NotNull @Min(value = 0, message = "Digite uma idade valida, nao pode ser menor que 0") @Max(value = 120, message = "Digite uma idade valida") int age,
            String description, @NotNull String eyesColor, @NotNull String hairColor,
            @NotNull @Min(value = 0, message = "Digite uma valor valido, nao pode ser menor que 0") double height,
            @NotNull @Min(value = 0, message = "Digite uma valor valido, nao pode ser menor que 0") double weight,
            @NotNull @Min(value = 0, message = "Digite uma valor valido, nao pode ser menor que 0") double waistline,
            @NotNull @Min(value = 0, message = "Digite uma valor valido, nao pode ser menor que 0") double hip,
            @NotNull @Min(value = 0, message = "Digite uma valor valido, nao pode ser menor que 0") double bust,
            List<Requests> requests) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.description = description;
        this.eyesColor = eyesColor;
        this.hairColor = hairColor;
        this.height = height;
        this.weight = weight;
        this.waistline = waistline;
        this.hip = hip;
        this.bust = bust;
        this.requests = requests;
    }

    public Model() {
    }



}