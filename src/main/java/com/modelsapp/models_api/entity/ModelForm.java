package com.modelsapp.models_api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.UUID;

@Entity
public class ModelForm {
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

    public ModelForm() {
    }

    public ModelForm(UUID id, @NotNull String name,
            @NotNull @Min(value = 0, message = "Digite uma idade valida, nao pode ser menor que 0") @Max(value = 120, message = "Digite uma idade valida") int age,
            String description, @NotNull String eyesColor, @NotNull String hairColor,
            @NotNull @Min(value = 0, message = "Digite uma valor valido, nao pode ser menor que 0") double height,
            @NotNull @Min(value = 0, message = "Digite uma valor valido, nao pode ser menor que 0") double weight,
            @NotNull @Min(value = 0, message = "Digite uma valor valido, nao pode ser menor que 0") double waistline,
            @NotNull @Min(value = 0, message = "Digite uma valor valido, nao pode ser menor que 0") double hip,
            @NotNull @Min(value = 0, message = "Digite uma valor valido, nao pode ser menor que 0") double bust) {
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
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEyesColor() {
        return eyesColor;
    }

    public void setEyesColor(String eyesColor) {
        this.eyesColor = eyesColor;
    }

    public String getHairColor() {
        return hairColor;
    }

    public void setHairColor(String hairColor) {
        this.hairColor = hairColor;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getWaistline() {
        return waistline;
    }

    public void setWaistline(double waistline) {
        this.waistline = waistline;
    }

    public double getHip() {
        return hip;
    }

    public void setHip(double hip) {
        this.hip = hip;
    }

    public double getBust() {
        return bust;
    }

    public void setBust(double bust) {
        this.bust = bust;
    }
}
