package com.modelsapp.models_api.Entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "roles")
public class User implements Serializable {
    private Long id;
    private String name;

    private String password;
    @ManyToMany(mappedBy = "users")
    private List<Role> roles;
}
