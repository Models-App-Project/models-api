package com.modelsapp.models_api.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;

public class AuthenticatedUser {

    private String username;
    private String password;

    public AuthenticatedUser(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public AuthenticatedUser() {
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

}