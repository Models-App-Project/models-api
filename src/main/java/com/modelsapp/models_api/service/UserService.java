package com.modelsapp.models_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.modelsapp.models_api.entity.Role;
import com.modelsapp.models_api.entity.User;
import com.modelsapp.models_api.repository.IRoleRepository;
import com.modelsapp.models_api.repository.IUserRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    @Autowired
    private IUserRepository iUserRepository;

    @Autowired
    private IRoleRepository iRoleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Optional<User> obterUsuarioId(Long usuarioId) {
        return this.iUserRepository.findById(usuarioId);
    }

    public User salvarUsuario(User usuario) {
        usuario.setRoles(usuario.getRoles()
                .stream()
                .map(role -> iRoleRepository.findByName(role.getName()))
                .toList());
        // CRIPTOGRAFIA
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return this.iUserRepository.save(usuario);
    }

    public User atualizarUsuario(User usuario) {
        usuario.setRoles(usuario.getRoles()
                .stream()
                .map(role -> iRoleRepository.findByName(role.getName()))
                .toList());
        // CRIPTOGRAFIA
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return this.iUserRepository.save(usuario);
    }

    public void excluirUsuario(User usuario) {
        this.iUserRepository.deleteById(usuario.getId());
    }

    public List<User> obterUsuarios() {
        return this.iUserRepository.findAll();
    }
}
