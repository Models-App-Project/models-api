package com.modelsapp.models_api.service;

import com.modelsapp.models_api.entity.Model;
import com.modelsapp.models_api.entity.Requests;
import com.modelsapp.models_api.repository.ModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
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
    @Autowired
    private RequestsServices requestsServices;

    private String defaultLocation = "/models/";

    // Método para buscar todas as modelos
    public List<Model> findAllModels() {
        return modelRepository.findAll();
    }

    // Método para salvar uma nova modelo
    public Model saveModel(Model model, List<String> URL) throws ModelException {
        try {
            //modelRepository.save(model);
            List<FileStorage> photosLocation = savePhotos(URL, model);
            model.setPhotos(photosLocation);
            Requests newRequest = new Requests(null, false, LocalDateTime.now(), model);
            model.setRequest(newRequest);
            return modelRepository.save(model);
        } catch (Exception e) {
            throw new ModelException("Erro ao salvar modelo.\n" + e.toString());
        }

    }

    //Método para atualizar fotos de uma modelo
    public List<FileStorage> updateModelPhotos(Model model, List<String> photos) {
        model.getPhotos().forEach(fileStorage -> {
           fileStorageService.deleteFileById(fileStorage.getId());
        });
        List<FileStorage> photosLocation = savePhotos(photos, model);
        return photosLocation;

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
            requestsServices.deleteRequestById(model.getRequest().getId());
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
            requestsServices.deleteRequestById(model.getRequest().getId());
            modelRepository.deleteModelByName(name);
        } catch (Exception e) {
            throw new ModelException("Erro ao excluir a modelo.\n" + e.toString());
        }

    }

    // Método para atualizar uma modelo
    public Model updateModel(Model newModelData, List<String> photos) {
        List<FileStorage> savedPhotos;
        if(photos != null) {
           savedPhotos = updateModelPhotos(newModelData, photos);
           newModelData.setPhotos(savedPhotos);
        }
        return modelRepository.save(newModelData);
    }

    //Recebe como parâmetro uma instância da classe modelo, cujo os atributos que estão preenchidos são os filtros que o usuário deseja aplicar.
    public List<Model> findModelsByFilters(Model modelToFind) {
        Specification<Model> spec = Specification.where(ModelRepository.hasModel(modelToFind));
        return modelRepository.findAll(spec);
    }

    //Método para salvar fotos de uma modelo
    private List<FileStorage> savePhotos(List<String> URL, Model model) {
        List<FileStorage> photosLocation = new ArrayList<>();

        URL.forEach(photo -> {
            String uploadDir = defaultLocation + model.getName() + "/profile/" + photo;
            FileStorage fileStorage = fileStorageService.saveFile(photo, uploadDir, null, model);
            photosLocation.add(fileStorage);
        });

        return photosLocation;
    }

}
