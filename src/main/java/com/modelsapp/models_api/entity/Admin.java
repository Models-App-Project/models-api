package com.modelsapp.models_api.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import com.modelsapp.models_api.permission.EnumPermission;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Getter
@Setter
public class Admin extends User{

    public Admin() {
        super();
        this.setRoles(new ArrayList<Role>());
        this.getRoles().add(new Role(EnumPermission.ADMINISTRADOR));
    }

}
