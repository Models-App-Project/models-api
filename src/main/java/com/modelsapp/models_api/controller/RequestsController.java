package com.modelsapp.models_api.controller;

import com.modelsapp.models_api.Exceptions.RequestException;
import com.modelsapp.models_api.entity.Model;
import com.modelsapp.models_api.entity.Requests;
import com.modelsapp.models_api.service.ModelService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.modelsapp.models_api.service.RequestsServices;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/requests")
public class RequestsController {

    @Autowired
    private RequestsServices requestsServices;

    @Autowired
    private ModelService modelService;

    @GetMapping("/getAllRequests")
    public ResponseEntity<List<Requests>> getAllRequests() {
        try {
            List<Requests> requests = requestsServices.findAllRequests();
            return new ResponseEntity<>(requests, HttpStatus.OK);
        } catch (RequestException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

    @GetMapping("/getRequestsByFilter")
    public ResponseEntity<List<Requests>> getRequestsByFilter(@RequestBody Model model, LocalDateTime startDate, LocalDateTime endDate, String status) {
        try {
            List<Requests> requests = requestsServices.findRequestsByFilter(model, startDate, endDate, status);
            return new ResponseEntity<>(requests, HttpStatus.OK);
        } catch (RequestException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

    @GetMapping("/getRequestById")
    public ResponseEntity<Requests> getRequestById(@RequestBody UUID requestID) {
        try {
            Requests request = requestsServices.findRequestById(requestID);
            return new ResponseEntity<>(request, HttpStatus.OK);
        } catch (RequestException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/getModelRequest")
    public ResponseEntity<Model> getModelDetailsRequest(@RequestBody Requests request) {
        try {
            Model model = requestsServices.getModelRequestDetails(request);
            return new ResponseEntity<>(model, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/saveRequest")
    public ResponseEntity<Requests> saveRequest(@RequestBody Requests request) {
        try {
            Requests savedRequest = requestsServices.saveRequest(request);
            return new ResponseEntity<>(savedRequest, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/updateRequest")
    public ResponseEntity<Requests> updateRequest(@RequestBody UUID requestID, Requests request) {
        try {
            Requests updatedRequest = requestsServices.updateRequest(requestID, request);
            return new ResponseEntity<>(updatedRequest, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/deleteRequest")
    public ResponseEntity<String> deleteRequest(@RequestBody UUID requestID) {
        try {
            requestsServices.deleteRequestById(requestID);
            return new ResponseEntity<>("Requisição deletada", HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno ao deletar a requisição. Tente novamente mais tarde.");
        }
    }

    @DeleteMapping("/deleteAllRequests")
    public ResponseEntity<String> deleteAllRequests() {
        try {
            requestsServices.deleteAllRequests();
            return new ResponseEntity<>("Todas as requisições foram deletadas", HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno ao deletar as requisições. Tente novamente mais tarde.");
        }
    }


}
