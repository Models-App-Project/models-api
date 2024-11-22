package com.modelsapp.models_api.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.modelsapp.models_api.permission.EnumPermission;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "roles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column
    @Enumerated(EnumType.STRING)
    private EnumPermission name;

    @ManyToMany(mappedBy = "roles")
    @JsonBackReference("user-roles") // Diferencia a relação com `User.roles`
    private List<User> users;

}
