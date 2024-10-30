package com.modelsapp.models_api.entity;

import lombok.Setter;
import lombok.Getter;


@Getter
@Setter
public class AuthenticatedUser {

    private String username;
    private String password;
    public AuthenticatedUser(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public AuthenticatedUser() {
    }

}