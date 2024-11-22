package com.modelsapp.models_api.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticatedUser {

    private String username;
    private String password;



}