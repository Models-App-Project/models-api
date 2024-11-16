package com.modelsapp.models_api.service;


import com.modelsapp.models_api.Exceptions.UserException;
import com.modelsapp.models_api.entity.FileStorage;
import com.modelsapp.models_api.permission.EnumPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import com.modelsapp.models_api.entity.Role;
import com.modelsapp.models_api.entity.User;
import com.modelsapp.models_api.repository.IUserRepository;
import org.springframework.web.multipart.MultipartFile;


import java.util.*;

@Service
@Transactional
public class UserService {
    @Autowired
    private IUserRepository iUserRepository;

    @Autowired
    private RoleServices RoleServices;

   @Autowired
   private PasswordEncoder passwordEncoder;

    @Autowired
    private FileStorageService fileStorageService;



    private String defaultLocation = "/downloads/users/";

    public Optional<User> obterUsuarioId(UUID usuarioId) {
        return this.iUserRepository.getUserById(usuarioId);
    }

    public User salvarUsuario(User usuario) throws UserException{



            usuario.setRoles(usuario.getRoles()
                    .stream()
                    .map(role -> RoleServices.findUserByName(role.getName()))
                    .toList());
            // CRIPTOGRAFIA
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
            return this.iUserRepository.save(usuario);


    }

    public void salvarPhotosUsuario(User usuario, List<MultipartFile> photos) throws UserException {
        if(photos.size() > 8) {
            throw new UserException("O número máximo de fotos permitido é 8.");
        } else {
            List<FileStorage> photosLocation = savePhotos(photos, usuario);
            usuario.setPhotos(photosLocation);
            iUserRepository.save(usuario);
        }
    }

    public User atualizarUsuario(User usuario) throws UserException {

            usuario.setRoles(usuario.getRoles()
                    .stream()
                    .map(role -> RoleServices.findUserByName(role.getName()))
                    .toList());
            // CRIPTOGRAFIA
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
            return this.iUserRepository.save(usuario);


    }

    public void atualizarPhotosUsuario(User usuario, List<MultipartFile> photos) throws  UserException {
        if(photos.size() > 8) {
            throw new UserException("O número máximo de fotos permitido é 8.");
        } else {
            usuario.getPhotos().forEach(fileStorage -> {
                fileStorageService.deleteFileById(fileStorage.getId());
            });
            List<FileStorage> photosLocation = savePhotos(photos, usuario);
            usuario.setPhotos(photosLocation);
            iUserRepository.save(usuario);
        }
    }

    public List<User> getUsersByRole(EnumPermission role) throws UserException {
         Role roleFound = RoleServices.findUserByName(role);
         Optional<List<User>> filtredByRoleUsers = this.iUserRepository.getUsersByRoles(roleFound);

         if(filtredByRoleUsers.isPresent()) {
             return filtredByRoleUsers.get();
         } else {
            throw new UserException("Não foi encontrado nenhum usuário com o papel " + role);
         }
    }

    public JSONObject getUserPhotos (List<FileStorage> fileStorages, UUID userId) throws UserException {
        JSONObject userPhotos = new JSONObject();
        JSONArray photos = new JSONArray();

        userPhotos.put("userId", userId);

        photos.add(fileStorageService.getFiles(fileStorages));
        userPhotos.put("photos", photos);

        return userPhotos;
    }

    public void excluirUsuario(User usuario) {
        usuario.getPhotos().forEach(fileStorage -> {
            fileStorageService.deleteFileById(fileStorage.getId());
        });
        this.iUserRepository.deleteUserById(usuario.getId());
    }

    public List<User> obterUsuarios() { return this.iUserRepository.findAll(); }

    public User getloggedInUser() throws UserException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UserException("Usuário não encontrado ou não está logado.");
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            return iUserRepository.findByUsername(username)
                    .orElseThrow(() -> new UserException("Usuário não encontrado"));
        } else {
            throw new UserException("Usuário não encontrado ou não está logado");
        }
    }

    public boolean isUserLoggedIn(List<Role> rolesFilter) {
        try {
            User userIsLoged = this.getloggedInUser();
            if(Objects.isNull(rolesFilter)) {
                return true;
            }
            else if (userIsLoged.getRoles().equals(rolesFilter)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    private List<FileStorage> savePhotos(List<MultipartFile> photos, User user) {
        List<FileStorage> photosLocation = new ArrayList<>();

        photos.forEach(photo -> {

            String uploadDir = defaultLocation + user.getUsername() + "/profile/" + photo.getOriginalFilename();

            FileStorage fileStorage = fileStorageService.saveFile(photo, uploadDir, user, null);
            photosLocation.add(fileStorage);
        });

        return photosLocation;
    }

}
