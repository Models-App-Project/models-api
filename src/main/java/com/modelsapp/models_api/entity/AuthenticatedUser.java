package com.modelsapp.models_api.entity;

import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticatedUser {

    private String username;
    private String password;



}