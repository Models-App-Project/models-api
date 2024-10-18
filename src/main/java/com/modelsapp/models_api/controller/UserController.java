package com.modelsapp.models_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.modelsapp.models_api.entity.User;
import com.modelsapp.models_api.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<String> salvarUsuario(@RequestBody User usuario) {
        User usuariosalvo = userService.salvarUsuario(usuario);

        return new ResponseEntity<>("Novo usuário criado " + usuariosalvo.getUsername(), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<String> atualizarUsuario(@RequestBody User usuario) {
        User usuariosalvo = userService.salvarUsuario(usuario);
        return new ResponseEntity<>("Novo usuário criado " + usuariosalvo.getUsername(), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<User>> obterUsuarios() {
        return new ResponseEntity<>(userService.obterUsuarios(), HttpStatus.OK);
    }

    @DeleteMapping
    public void excluirUsuario(@RequestBody User usuario) {
        userService.excluirUsuario(usuario);
    }

}