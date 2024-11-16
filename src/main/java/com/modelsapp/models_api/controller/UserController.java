package com.modelsapp.models_api.controller;

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



    @PostMapping("/create")
    public ResponseEntity<String> salvarUsuario(@RequestBody User usuario, @RequestParam("file") List<MultipartFile> photos) {
        if (bucket.tryConsume(1)) {
            User usuariosalvo = userService.salvarUsuario(usuario);
            userService.salvarPhotosUsuario(usuariosalvo, photos);
            return new ResponseEntity<>("Usuário salvo com sucesso! " + usuariosalvo.getUsername(), HttpStatus.CREATED);
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }

    @PutMapping("/update")
    public ResponseEntity<String> atualizarUsuario(@RequestBody User usuario, @RequestParam("file") List<MultipartFile> photos) {
        if (bucket.tryConsume(1)) {
            User usuariosalvo = userService.salvarUsuario(usuario);
            userService.atualizarPhotosUsuario(usuariosalvo, photos);
            return new ResponseEntity<>("Usuário atualizado " + usuariosalvo.getUsername(), HttpStatus.CREATED);
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity< Map<List<User>, List<JSONObject>> > obterUsuarios() {

        if (bucket.tryConsume(1)) {

            try{
                List<User> users = userService.obterUsuarios();
                List<JSONObject> usersPhotos = new ArrayList<>();
                users.forEach(user -> {
                    if(user.getPhotos().size() > 0) {
                        usersPhotos.add(userService.getUserPhotos(user.getPhotos(), user.getId() ));
                    }
                });
                Map<List<User>, List<JSONObject>> response = Map.of(users, usersPhotos);

                return new ResponseEntity<>(response, HttpStatus.OK);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }

        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }

    @GetMapping("/getUser/{id}")
    public ResponseEntity< Map< User, JSONObject > > obterUsuario(@PathVariable UUID id) {
        if (bucket.tryConsume(1)) {
            try {
                Optional<User> user = userService.obterUsuarioId(id);
                User requiredUser = user.get();
                JSONObject userPhotos = new JSONObject();
                if(requiredUser.getPhotos().size() > 0) {
                    userPhotos = userService.getUserPhotos(requiredUser.getPhotos(), requiredUser.getId());
                }
                Map<User, JSONObject> response = Map.of(requiredUser, userPhotos);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
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