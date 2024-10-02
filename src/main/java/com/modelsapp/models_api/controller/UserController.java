package com.modelsapp.models_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.modelsapp.models_api.entity.User;
import com.modelsapp.models_api.service.UserService;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;

import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    private Bandwidth limit = Bandwidth.classic(20, Refill.greedy(20, Duration.ofMinutes(1)));
    private final Bucket bucket = Bucket.builder()
            .addLimit(limit)
            .build();

    @PostMapping
    public ResponseEntity<String> salvarUsuario(@RequestBody User usuario) {

        if (bucket.tryConsume(1)) {
            User usuariosalvo = userService.salvarUsuario(usuario);
            return new ResponseEntity<>("Novo usuário criado " + usuariosalvo.getUsername(), HttpStatus.CREATED);
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }

    @PutMapping
    public ResponseEntity<String> atualizarUsuario(@RequestBody User usuario) {

        if (bucket.tryConsume(1)) {
            User usuariosalvo = userService.salvarUsuario(usuario);
            return new ResponseEntity<>("Usuário atualizado " + usuariosalvo.getUsername(), HttpStatus.CREATED);
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }

    @GetMapping
    public ResponseEntity<List<User>> obterUsuarios() {
        if (bucket.tryConsume(1)) {
            return new ResponseEntity<>(userService.obterUsuarios(), HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }

    @DeleteMapping
    public ResponseEntity<String> excluirUsuario(@RequestBody User usuario) {
        if (bucket.tryConsume(1)) {
            userService.excluirUsuario(usuario);
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }

}