package com.modelsapp.models_api.controller;

import com.modelsapp.models_api.entity.ModelForm;
import com.modelsapp.models_api.service.EmailService;
import com.modelsapp.models_api.service.ModelFormService;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

@RestController
@RequestMapping("/modelsforms")
public class ModelFormController {
    @Autowired
    private ModelFormService modelFormService;

    // Endpoint para enviar um formulário da modelo
    @PostMapping("/sendForm")
    public ResponseEntity<ModelForm> sendModelForm(@RequestBody ModelForm model) {
        Optional<ModelForm> savedModelForm = modelFormService.saveModelForm(model);

        if (savedModelForm.isPresent()) {
            ModelForm form = savedModelForm.get();

            String assunto = "Confirmação de envio de formulário";
            String mensagem = "Seu formulário foi enviado com sucesso!\n\nObrigado por entrar em contato. Em breve retornaremos. \n\nAtenciosamente, \nEquipe ModelsApp";

            // Enviar email para o usuário
            EmailService.enviarEmailConfirmacao(form.getEmail(), assunto, mensagem);

            return savedModelForm.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    // Endpoint para buscar todos os formulários das modelos
    @GetMapping("/findAllForms")
    public ResponseEntity<List<ModelForm>> getAllModelForms() {
        List<ModelForm> modelForms = modelFormService.findAllModelForms();
        return ResponseEntity.ok(modelForms);
    }

    // Endpoint para buscar um formulário da modelo pelo nome
    @GetMapping("/findByName")
    public ResponseEntity<ModelForm> getModelFormByName(@RequestParam String name) {
        Optional<ModelForm> modelForm = modelFormService.findModelFormByName(name);
        return modelForm.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint para buscar um formulário da modelo pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<ModelForm> getModelFormById(@PathVariable UUID id) {
        Optional<ModelForm> modelForm = modelFormService.findModelFormById(id);
        return modelForm.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint para deletar um formulário da modelo por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteModelFormById(@PathVariable UUID id) {
        modelFormService.deleteModelFormById(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint para deletar um formulário da modelo por nome
    @DeleteMapping("/deleteByName")
    public ResponseEntity<Void> deleteModelFormByName(@RequestParam String name) {
        modelFormService.deleteModelFormByName(name);
        return ResponseEntity.noContent().build();
    }
}
