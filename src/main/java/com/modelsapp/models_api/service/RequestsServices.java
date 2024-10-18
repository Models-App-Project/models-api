package com.modelsapp.models_api.service;

import com.modelsapp.models_api.Execptions.RequestNotFoundException;
import com.modelsapp.models_api.entity.Model;
import com.modelsapp.models_api.repository.RequestsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.modelsapp.models_api.entity.Requests;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class RequestsServices {

    @Autowired
    private RequestsRepository requestsRepository;
    @Autowired
    private ModelService modelService;


    //ENCONTRAR=============================================================

    public List<Requests> findAllRequests() throws RequestNotFoundException, Exception {
        try {
            List<Requests> requests = requestsRepository.findAll();
            if(requests.isEmpty()) {
                throw new RequestNotFoundException("Nenhuma requisição encontrada");
            } else {
                return requests;
            }
        } catch (Exception e) {
            throw new Exception("Ocorreu um erro inesperado:" + e);
        }


    }
    //***************************************************************************************
    //Eu sei que esse método não ta escrito da melhor maneira, mas vou melhorar ele depois
    //***************************************************************************************
    public List<Requests> findRequestsByFilter(Model model, LocalDateTime startDate, LocalDateTime endDate, String status) throws RequestNotFoundException, Exception {
        try{
            Specification<Requests> spec = Specification.where(null);

            if(startDate != null && endDate != null) {
                spec = spec.and(RequestsRepository.hasDateRange(startDate, endDate));
            }
            if(!status.isEmpty()) {
                spec = spec.and(RequestsRepository.hasStatus(status));
            }

            List<Requests> requestsToFilter =  requestsRepository.findAll(spec);

            if(requestsToFilter.isEmpty()) {
                throw new RequestNotFoundException("Nenhuma requisição encontrada");
            } else {
                if(model == null) {
                    return requestsToFilter;
                } else {
                    requestsToFilter = requestsToFilter.stream().filter(request -> request.getModel().equals(model)).toList();
                    if(requestsToFilter.isEmpty()) {
                        throw new RequestNotFoundException("Nenhuma requisição encontrada");
                    } else {
                        return requestsToFilter;
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception("Ocorreu um erro inesperado:" + e);
        }
    }


    public Requests findRequestById(UUID requestID) throws RequestNotFoundException, Exception {

        try {
            Optional<Requests> request = requestsRepository.findRequestById(requestID);

            if(request.isPresent()) {
                return request.get();
            } else {
                throw new RequestNotFoundException("Request not found");
            }
        } catch (Exception e) {
            throw new Exception("Ocorreu um erro inesperado:" + e);
        }
    }

    public Model getModelRequestDetails(Requests request) throws Exception {

        try {
            return request.getModel();
        } catch (Exception e) {
            throw new Exception("Ocorreu um erro inesperado:" + e);
        }

    }


    //=======================================================================


    //SALVAR===============================================================
    public Requests saveRequest(Requests request) throws Exception {
        try {
            return requestsRepository.save(request);
        } catch (Exception e) {
            throw new Exception("Ocorreu um erro inesperado:" + e);
        }
    }
    //=======================================================================


    //ATUALIZAR=============================================================
    public Requests updateRequest(UUID id, Requests newRequestData) throws Exception {

        try {
            return requestsRepository.findRequestById(id)
                    .map(existingRequest -> {
                        existingRequest.setStatus(newRequestData.getStatus());
                        existingRequest.setRequestDate(newRequestData.getRequestDate());
                        existingRequest.setModel(newRequestData.getModel());
                        return requestsRepository.save(existingRequest);
                    }).orElseGet(() -> {
                        newRequestData.setId(id);
                        return requestsRepository.save(newRequestData);
                    });
        } catch (Exception e) {
            throw new Exception("Ocorreu um erro inesperado:" + e);
        }

    }

    //=======================================================================

    //DELETAR===============================================================
    public void deleteRequestById(UUID id) throws Exception {
        try {
            requestsRepository.deleteById(id);
        } catch (Exception e) {
            throw new Exception("Ocorreu um erro inesperado:" + e);
        }
    }

    public void deleteAllRequests() throws Exception {
        try {
            requestsRepository.deleteAll();
        } catch (Exception e) {
            throw new Exception("Ocorreu um erro inesperado:" + e);
        }

    }

    //=======================================================================
}
