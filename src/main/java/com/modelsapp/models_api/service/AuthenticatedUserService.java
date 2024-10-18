package com.modelsapp.models_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.modelsapp.models_api.repository.IUserRepository;

import java.util.List;


@Service
@Transactional
public class AuthenticatedUserService implements UserDetailsService {
    @Autowired
    private IUserRepository iUserRepository;


    public UserDetails loadUserByUsername(String username) {

        com.modelsapp.models_api.entity.User usuario = iUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário: " + username + " não foi encontrado"));


        List<SimpleGrantedAuthority> roles = usuario.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().toString()))
                .toList();

        return new User(usuario.getUsername(), usuario.getPassword(), roles);
    }


}