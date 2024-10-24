package com.modelsapp.models_api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.modelsapp.models_api.permission.EnumPermission;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "users")
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true, length = 50)
    @NonNull
    private String username;

    @Column(length = 150)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NonNull
    private String password;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "role_id"}))
    private List<Role> roles;

    public String getUsername() {
        return this.username;
    }

    public Optional<User> getRoles() {
        return Optional.ofNullable(this);
    }

    public EnumPermission getName() {
        return this.getName();
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String encode) {
        this.password = encode;
    }

    public Long getId() {
        return this.id;
    }

    public void setRoles(List<Role> list) {
        this.roles = list;
    }
}