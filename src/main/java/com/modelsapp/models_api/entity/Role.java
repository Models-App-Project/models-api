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

    public Role(Long id, EnumPermission name, List<User> users) {
        this.id = id;
        this.name = name;
        this.users = users;
    }

    public Role() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EnumPermission getName() {
        return name;
    }

    public void setName(EnumPermission name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
