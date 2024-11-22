package com.modelsapp.models_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.modelsapp.models_api.entity.User;
import com.modelsapp.models_api.service.FileStorageService;
import com.modelsapp.models_api.service.UserService;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;

import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private Bandwidth limit = Bandwidth.classic(20, Refill.greedy(20, Duration.ofMinutes(1)));
    private final Bucket bucket = Bucket.builder()
            .addLimit(limit)
            .build();

    @Autowired
    private UserService userService;

    @Autowired
    private FileStorageService fileStorageService;



    @PostMapping(value = "/save", consumes = "multipart/form-data")
    public ResponseEntity<String> salvarUsuario(
            @RequestPart("user") String userJson,
            @RequestPart("photos") List<String> photos,
            @RequestParam("role") String role
    ) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            User user = objectMapper.readValue(userJson, User.class);

            User savedUser = userService.salvarUsuario(user, photos, role);
            return new ResponseEntity<>("Novo administrador criado: " + savedUser.getUsername(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao criar administrador: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<String> atualizarUsuario( @RequestPart("user") String userJson,
                                                  @RequestPart("photos") List<String> userPhotos) {
        try {
            if (!bucket.tryConsume(1)) throw new Exception("Too many requests");
            ObjectMapper objectMapper = new ObjectMapper();
            User user = objectMapper.readValue(userJson, User.class);


            User updatedAdmin = userService.atualizarUsuario(user, userPhotos);
            return new ResponseEntity<>("Administrador atualizado " + updatedAdmin.getUsername(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao atualizar administrador." + e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity< List<User> > obterUsuarios() {

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


    @DeleteMapping("/deleteUser")
    public ResponseEntity<String> excluirUsuario(@RequestBody User usuario) {
        if (bucket.tryConsume(1)) {
            userService.excluirUsuario(usuario);
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }

}