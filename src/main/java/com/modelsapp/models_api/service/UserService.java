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
    private RoleServices roleServices;

   @Autowired
   private PasswordEncoder passwordEncoder;

    @Autowired
    private FileStorageService fileStorageService;



    private String defaultLocation = "/users/";

    public Optional<User> obterUsuarioId(UUID usuarioId) {
        return this.iUserRepository.getUserById(usuarioId);
    }

    public User salvarUsuario(User usuario, List<String> photos, String role) throws UserException{

            try {
                Role newRole = roleServices.getNewRoleByString(role);

                List<FileStorage> savedPhotos = salvarPhotosUsuario(usuario, photos);

                usuario.setPhotos(savedPhotos);
                usuario.setRoles(List.of(newRole));
                roleServices.addNewUser(newRole, usuario);
                roleServices.saveRole(newRole);


                usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
                return this.iUserRepository.save(usuario);
            } catch (Exception e) {
                throw new UserException(e.toString());
            }



    }

    public List<FileStorage> salvarPhotosUsuario(User usuario, List<String> photos) throws UserException {
        if(photos.size() > 8) {
            throw new UserException("O número máximo de fotos permitido é 8.");
        } else {
            List<FileStorage> savedPhotos = new ArrayList<>();
            photos.forEach(photo -> {
                String fileName = defaultLocation + usuario.getUsername() + "/";
                FileStorage savedPhoto = fileStorageService.saveFile(photo, fileName, usuario, null);
                savedPhotos.add(savedPhoto);
            });

            return savedPhotos;
        }
    }

    public User atualizarUsuario(User usuario, List<String> photos) throws UserException {


            try{
                usuario.getPhotos().forEach(fileStorage -> {
                    fileStorageService.deleteFileById(fileStorage.getId());
                });

                List<FileStorage> savedPhotos = salvarPhotosUsuario(usuario, photos);

                usuario.setPhotos(savedPhotos);


                usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
                return this.iUserRepository.save(usuario);
            } catch (Exception e) {
                throw new UserException(e.toString());
            }



    }

    public void atualizarPhotosUsuario(User usuario, List<String> photos) throws  UserException {
        if(photos.size() > 8) {
            throw new UserException("O número máximo de fotos permitido é 8.");
        } else {
            usuario.getPhotos().forEach(fileStorage -> {
                fileStorageService.deleteFileById(fileStorage.getId());
            });
            List<FileStorage> photosLocation = salvarPhotosUsuario(usuario, photos);
            usuario.setPhotos(photosLocation);
            iUserRepository.save(usuario);
        }
    }

    public List<User> getUsersByRole(EnumPermission role) throws UserException {
         Role roleFound = roleServices.findUserByName(role);
         Optional<List<User>> filtredByRoleUsers = this.iUserRepository.getUsersByRoles(roleFound);

         if(filtredByRoleUsers.isPresent()) {
             return filtredByRoleUsers.get();
         } else {
            throw new UserException("Não foi encontrado nenhum usuário com o papel " + role);
         }
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

   /* private List<FileStorage> savePhotos(List<MultipartFile> photos, User user) {
        List<FileStorage> photosLocation = new ArrayList<>();

        photos.forEach(photo -> {

            String uploadDir = defaultLocation + user.getUsername() + "/profile/" + photo.getOriginalFilename();

            FileStorage fileStorage = fileStorageService.saveFile(photo, uploadDir, user, null);
            photosLocation.add(fileStorage);
        });

        return photosLocation;
    }*/

}
