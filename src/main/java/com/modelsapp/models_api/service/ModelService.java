package com.modelsapp.models_api.service;

import com.modelsapp.models_api.Exceptions.ModelException;
import com.modelsapp.models_api.entity.FileStorage;
import com.modelsapp.models_api.entity.Model;
import com.modelsapp.models_api.entity.User;
import com.modelsapp.models_api.repository.ModelRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.UUID;
import java.util.List;
import java.util.Optional;



@Service
public class ModelService {
    @Autowired
    private ModelRepository modelRepository;
    @Autowired
    private FileStorageService fileStorageService;

    private String defaultLocation = "/downloads/models/";

    // Método para buscar todas as modelos
    public List<Model> findAllModels() {
        return modelRepository.findAll();
    }

    //Método para buscar fotos das modelos
    public JSONObject findModelPhotos(List<FileStorage> fileStorages, UUID modelID) throws ModelException{

        List<Exception> exceptions = new ArrayList<>();

        JSONObject modelPhotos = new JSONObject();

        modelPhotos.put("modelId", modelID);
        modelPhotos.put("photos", new JSONArray().add(fileStorageService.getFiles(fileStorages)));

        if (!exceptions.isEmpty()) {
            throw new ModelException("Erros ao buscar fotos: " + exceptions);
        }

        return modelPhotos;
    }

    // Método para salvar uma nova modelo
    public Model saveModel(Model model) { return modelRepository.save(model); }

    //Método para salvar fotos de uma modelo
    public void saveModelPhotos(Model model, List<MultipartFile> photos) {
        List<FileStorage> photosLocation = savePhotos(photos, model);
        model.setPhotos(photosLocation);
        modelRepository.save(model);
    }

    //Método para atualizar fotos de uma modelo
    public void updateModelPhotos(Model model, List<MultipartFile> photos) {
        model.getPhotos().forEach(fileStorage -> {
           fileStorageService.deleteFileById(fileStorage.getId());
        });
        List<FileStorage> photosLocation = savePhotos(photos, model);
        model.setPhotos(photosLocation);
        modelRepository.save(model);
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
    public void deleteModelById(UUID id) throws ModelException {

        Optional<Model> currentModel = modelRepository.findModelById(id);
        try{
            Model model = currentModel.get();
            model.getPhotos().forEach(fileStorage -> {
                fileStorageService.deleteFileById(fileStorage.getId());
            });
            modelRepository.deleteById(id);
        } catch (Exception e) {
            throw new ModelException("Erro ao excluir a modelo.\n" + e.toString());
        }

    }

    // Método para deletar uma modelo por nome
    public void deleteModelByName(String name) throws ModelException {

        Optional<Model> currentModel = modelRepository.findModelByName(name);
        try{
            Model model = currentModel.get();
            model.getPhotos().forEach(fileStorage -> {
                fileStorageService.deleteFileById(fileStorage.getId());
            });
            modelRepository.deleteModelByName(name);
        } catch (Exception e) {
            throw new ModelException("Erro ao excluir a modelo.\n" + e.toString());
        }

    }

    // Método para atualizar uma modelo
    public Model updateModel(UUID id, Model newModelData) {
        return modelRepository.save(newModelData);
        /*return modelRepository.findModelById(id)
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
                .orElse(null);*/
    }

    //Recebe como parâmetro uma instância da classe modelo, cujo os atributos que estão preenchidos são os filtros que o usuário deseja aplicar.
    public List<Model> findModelsByFilters(Model modelToFind) {
        Specification<Model> spec = Specification.where(ModelRepository.hasModel(modelToFind));
        return modelRepository.findAll(spec);
    }

    //Método para salvar fotos de uma modelo
    private List<FileStorage> savePhotos(List<MultipartFile> photos, Model model) {
        List<FileStorage> photosLocation = new ArrayList<>();

        photos.forEach(photo -> {
            String uploadDir = defaultLocation + model.getName() + "/profile/" + photo.getOriginalFilename();
            FileStorage fileStorage = fileStorageService.saveFile(photo, uploadDir, null, model);
            photosLocation.add(fileStorage);
        });

        return photosLocation;
    }

}
