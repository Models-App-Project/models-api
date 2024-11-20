package com.modelsapp.models_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.modelsapp.models_api.entity.FileStorage;
import com.modelsapp.models_api.service.FileStorageService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import com.modelsapp.models_api.entity.User;
import com.modelsapp.models_api.service.UserService;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.web.multipart.MultipartFile;

import java.time.Duration;
import java.util.*;

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
            @RequestPart("photos") List<MultipartFile> photos,
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
                                                  @RequestPart("photos") List<MultipartFile> userPhotos) {
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

            try{
                List<User> users = userService.obterUsuarios();


                return new ResponseEntity<>(users, HttpStatus.OK);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }

        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }

    @GetMapping("/getUser/{id}")
    public ResponseEntity< User > obterUsuario(@PathVariable UUID id) {
        if (bucket.tryConsume(1)) {
            try {
                Optional<User> user = userService.obterUsuarioId(id);
                User requiredUser = user.get();
                return new ResponseEntity<>(requiredUser, HttpStatus.OK);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }


    @DeleteMapping("/deleteUser")
    public ResponseEntity<String> excluirUsuario(@RequestBody User usuario) {
        try {
            if (!bucket.tryConsume(1)) throw new Exception("Too many requests");
            userService.excluirUsuario(usuario);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao excluir administrador." + e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}