package com.modelsapp.models_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int age;
    private String description;
    private String eyesColor;
    private String hairColor;
    private double height;
    private double weight;
    private double waistline;
    private double hip;
    private double bust;

    // Getters and Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
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
