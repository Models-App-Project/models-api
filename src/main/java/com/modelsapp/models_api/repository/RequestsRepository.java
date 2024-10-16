package com.modelsapp.models_api.repository;

import com.modelsapp.models_api.entity.Model;
import com.modelsapp.models_api.entity.Requests;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RequestsRepository  extends JpaRepository<Requests, UUID>, JpaSpecificationExecutor<Requests> {

    // Método para buscar um requisição pelo ID
    public Optional<Requests> findRequestById(UUID id);

    // Método para buscar uma requisição pela modelo
    public Optional<Requests> findRequestsByModel(Model model);

    // Método para deletar uma requisição pelo ID
    public boolean deleteRequestById(UUID id);

    // Método para deletar uma requisição pela modelo
    public void deleteRequestByModel(Model model);


    public static Specification<Requests> hasStatus(String status) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("status"), status);
    }

    public static Specification<Requests> hasDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.between(root.get("requestDate"), startDate, endDate);
    }

}
