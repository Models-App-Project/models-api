package com.modelsapp.models_api.service;

import com.modelsapp.models_api.entity.Model;
import com.modelsapp.models_api.repository.ModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.Objects;
import java.util.UUID;
import java.util.List;
import java.util.Optional;

@Service
public class ModelService {
    @Autowired
    private ModelRepository modelRepository;

    // Método para buscar todas as modelos
    public List<Model> findAllModels() {
        return modelRepository.findAll();
    }

    // Método para salvar uma nova modelo
    public Model saveModel(@Valid @RequestBody Model model) {
        return modelRepository.save(model);
    }

    // Método para buscar uma modelo por ID
    public Optional<Model> findModelById(UUID id) {
        return modelRepository.findModelById(id);
    }

    // Método para buscar uma modelo por nome
    public Optional<Model> findModelByName(String name) {
        return modelRepository.findModelByName(name);
    }

    // Método para deletar uma modelo por ID
    public void deleteModelById(UUID id) {
        modelRepository.deleteById(id);
    }

    // Método para deletar uma modelo por nome
    public void deleteModelByName(String name) {
        modelRepository.findModelByName(name).ifPresent(model -> modelRepository.delete(model));
    }

    // Método para atualizar uma modelo
    public Model updateModel(UUID id, Model newModelData) {
        return modelRepository.findModelById(id)
                .map(existingModel -> {
                    existingModel.setName(newModelData.getName());
                    existingModel.setAge(newModelData.getAge());
                    existingModel.setDescription(newModelData.getDescription());
                    existingModel.setEyesColor(newModelData.getEyesColor());
                    existingModel.setHairColor(newModelData.getHairColor());
                    existingModel.setHeight(newModelData.getHeight());
                    existingModel.setWeight(newModelData.getWeight());
                    existingModel.setWaistline(newModelData.getWaistline());
                    existingModel.setHip(newModelData.getHip());
                    existingModel.setBust(newModelData.getBust());
                    return modelRepository.save(existingModel);
                })
                .orElse(null);
    }

    //Recebe como parâmetro uma instância da classe modelo, cujo os atributos que estão preenchidos são os filtros que o usuário deseja aplicar.
    public List<Model> findModelsByFilters(Model modelToFind) {
        return modelRepository.findAll().stream()
                .filter(model -> (modelToFind.getName() == null || model.getName().equals(modelToFind.getName())) &&
                        (Objects.isNull(modelToFind.getAge()) || model.getAge() == modelToFind.getAge()) &&
                        (Objects.isNull(modelToFind.getDescription()) || model.getDescription().equals(modelToFind.getDescription())) &&
                        (Objects.isNull(modelToFind.getEyesColor()) || model.getEyesColor().equals(modelToFind.getEyesColor())) &&
                        (Objects.isNull(modelToFind.getHairColor()) || model.getHairColor().equals(modelToFind.getHairColor())) &&
                        (Objects.isNull(modelToFind.getHeight()) || model.getHeight() == modelToFind.getHeight()) &&
                        (Objects.isNull(modelToFind.getWeight()) || model.getWeight() == modelToFind.getWeight()) &&
                        (Objects.isNull(modelToFind.getWaistline()) || model.getWaistline() == modelToFind.getWaistline()) &&
                        (Objects.isNull(modelToFind.getHip()) || model.getHip() == modelToFind.getHip()) &&
                        (Objects.isNull(modelToFind.getBust()) || model.getBust() == modelToFind.getBust()))
                .toList();
    }
}
