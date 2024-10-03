package com.modelsapp.models_api.Entity;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true, length = 20)
    private String name;

    @ManyToMany(mappedBy = "roles")
    private List<User> usuarios;
}