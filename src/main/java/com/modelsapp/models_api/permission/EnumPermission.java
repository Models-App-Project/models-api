package com.modelsapp.models_api.permission;

public enum EnumPermission {
    ADMINISTRADOR, SUB_ADMINISTRADOR, MODEL; 

    @Override
    public String toString() {
        return "ROLE_" + name();
    }
}