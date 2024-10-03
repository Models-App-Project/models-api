package com.modelsapp.models_api.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticatedUser {

    private String username;
    private String password;
}