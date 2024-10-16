package com.modelsapp.models_api.repository;

import com.modelsapp.models_api.entity.Model;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ModelRepository extends JpaRepository<Model, UUID>, JpaSpecificationExecutor<Model> {
    // Método para buscar uma modelo por ID
    public Optional<Model> findModelById(UUID id);

    // Método para buscar uma modelo por nome
    public Optional<Model> findModelByName(String name);

    // Método para deletar uma modelo por ID
    public void deleteModelById(UUID id);

    // Método para deletar uma modelo por nome
    public void deleteModelByName(String name);

    public static Specification<Model> hasModel(Model model) {

        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (!model.getName().isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + model.getName() + "%"));
            }
            if (model.getAge() != 0) {
                predicates.add(criteriaBuilder.equal(root.get("age"), model.getAge()));
            }
            if (!model.getDescription().isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("description"), "%" + model.getDescription() + "%"));
            }
            if (!model.getEyesColor().isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("eyesColor"), "%" + model.getEyesColor() + "%"));
            }
            if (!model.getHairColor().isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("hairColor"), "%" + model.getHairColor() + "%"));
            }
            if (model.getHeight() != 0) {
                predicates.add(criteriaBuilder.equal(root.get("height"), model.getHeight()));
            }
            if (model.getWeight() != 0) {
                predicates.add(criteriaBuilder.equal(root.get("weight"), model.getWeight()));
            }
            if (model.getWaistline() != 0) {
                predicates.add(criteriaBuilder.equal(root.get("waistline"), model.getWaistline()));
            }
            if (model.getHip() != 0) {
                predicates.add(criteriaBuilder.equal(root.get("hip"), model.getHip()));
            }
            if (model.getBust() != 0) {
                predicates.add(criteriaBuilder.equal(root.get("bust"), model.getBust()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

    }




}
