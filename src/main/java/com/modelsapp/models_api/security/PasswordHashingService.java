package com.modelsapp.models_api.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordHashingService {
    // Instância do encoder de senha
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Método para gerar o hash da senha
    public String hashPassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }
    //TODO REVISAR ESSA FUNCIONALIDADE DE ENVIAR TOKEN E ETC
    // Método para verificar se a senha correnponde ao hash
    public boolean checkPassword(String rawPassword, String hashedPassword) {
        return passwordEncoder.matches(rawPassword, hashedPassword);
    }
}
