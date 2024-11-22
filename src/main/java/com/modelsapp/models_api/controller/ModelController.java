package com.modelsapp.models_api.controller;

import com.modelsapp.models_api.entity.Model;
import com.modelsapp.models_api.service.ModelService;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;
import java.util.Optional;
import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping("/models")
public class ModelController {

    private Bandwidth limit = Bandwidth.classic(20, Refill.greedy(20, Duration.ofMinutes(1)));
    private final Bucket bucket = Bucket.builder()
            .addLimit(limit)
            .build();

    @Autowired
    private ModelService modelService;

    // Endpoint para cadastrar uma nova modelo
    @PostMapping("/add")
    public ResponseEntity<Model> addModel(@RequestPart("model") String model,
                                          @RequestPart("photos") List<String> photos
    ) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Model convertdModel = objectMapper.readValue(model, Model.class);

            Model savedModel= modelService.saveModel(convertdModel, photos);

            if (savedModel.getId() != null) {

                String assunto = "Confirmação de envio de formulário";
                String mensagem = "Seu formulário foi enviado com sucesso!\n\nObrigado por entrar em contato. Em breve retornaremos. \n\nAtenciosamente, \nEquipe ModelsApp";
                return new ResponseEntity<>(savedModel, HttpStatus.CREATED);
            } else {
                throw new Exception("Modelo não encontrado.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }

    // Endpoint para buscar uma modelo por ID
    @GetMapping("/{id}")
    public ResponseEntity<Model> getModelById(@PathVariable UUID id) {
        if (bucket.tryConsume(1)) {
            Optional<Model> model = modelService.findModelById(id);
            return model.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }

    // Endpoint para buscar todas as modelos
    @GetMapping("/findAll")
    public ResponseEntity<List<Model>> getAllModels() {
        if (bucket.tryConsume(1)) {
            List<Model> models = modelService.findAllModels();
            return ResponseEntity.ok(models);
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();

    }

    // Endpoint para buscar uma modelo por nome
    @GetMapping("/findByName")
    public ResponseEntity<Model> getModelByName(@RequestParam String name) {
        if (bucket.tryConsume(1)) {
            Optional<Model> model = modelService.findModelByName(name);
            return model.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }

    // Endpoint para deletar uma modelo por ID
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole(T(com.modelsapp.models_api.permission.EnumPermission).ADMINISTRADOR.toString(), T(com.modelsapp.models_api.permission.EnumPermission).SUB_ADMINISTRADOR.toString())")
    public ResponseEntity<Void> deleteModelById(@PathVariable UUID id) {
        if (bucket.tryConsume(1)) {
            modelService.deleteModelById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }

    // Endpoint para deletar uma modelo por nome
    @DeleteMapping("/deleteModel/deleteByName")
    public ResponseEntity<String> deleteModelByName(@RequestParam String name) throws ModelException {
        if (bucket.tryConsume(1)) {
            modelService.deleteModelByName(name);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }
}
