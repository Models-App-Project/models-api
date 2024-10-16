package com.modelsapp.models_api.controller;

import com.modelsapp.models_api.Execptions.RequestNotFoundException;
import com.modelsapp.models_api.entity.Model;
import com.modelsapp.models_api.entity.Requests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.modelsapp.models_api.service.RequestsServices;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/requests")
public class RequestsController {

    @Autowired
    private RequestsServices requestsServices;

    @GetMapping("/getAllRequests")
    public ResponseEntity<List<Requests>> getAllRequests() {
        try {
            List<Requests> requests = requestsServices.findAllRequests();
            return ResponseEntity.ok(requests);
        } catch (RequestNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

    @GetMapping("/getRequestsByFilter")
    public ResponseEntity<List<Requests>> getRequestsByFilter(@RequestBody Model model, LocalDateTime startDate, LocalDateTime endDate, String status) {
        try {
            List<Requests> requests = requestsServices.findRequestsByFilter(model, startDate, endDate, status);
            return ResponseEntity.ok(requests);
        } catch (RequestNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

    @GetMapping("/getRequestById")
    public ResponseEntity<Requests> getRequestById(@RequestBody UUID requestID) {
        try {
            Requests request = requestsServices.findRequestBYId(requestID);
            return ResponseEntity.ok(request);
        } catch (RequestNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/getModelRequest")
    public ResponseEntity<Model> getModelDetailsRequest(@RequestBody Requests request) {
        try {
            Model model = requestsServices.getModelRequestDetails(request);
            return ResponseEntity.ok(model);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/saveRequest")
    public ResponseEntity<Requests> saveRequest(@RequestBody Requests request) {
        try {
            Requests savedRequest = requestsServices.saveRequest(request);
            return ResponseEntity.ok(savedRequest);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/updateRequest")
    public ResponseEntity<Requests> updateRequest(@RequestBody UUID requestID, Requests request) {
        try {
            Requests updatedRequest = requestsServices.updateRequest(requestID, request);
            return ResponseEntity.ok(updatedRequest);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/deleteRequest")
    public ResponseEntity<String> deleteRequest(@RequestBody UUID requestID) {
        try {
            requestsServices.deleteRequestById(requestID);
            return ResponseEntity.ok("Requisição deletada");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno ao deletar a requisição. Tente novamente mais tarde.");
        }
    }

    @DeleteMapping("/deleteAllRequests")
    public ResponseEntity<String> deleteAllRequests() {
        try {
            requestsServices.deleteAllRequests();
            return ResponseEntity.ok("Todas as requisições foram deletadas");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno ao deletar as requisições. Tente novamente mais tarde.");
        }
    }


}
