package com.modelsapp.models_api.service;

import com.modelsapp.models_api.entity.Model;
import com.modelsapp.models_api.repository.ModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
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
        Specification<Model> spec = Specification.where(ModelRepository.hasModel(modelToFind));
        return modelRepository.findAll(spec);
    }
}
