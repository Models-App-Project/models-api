package com.modelsapp.models_api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.modelsapp.models_api.entity.Role;
import com.modelsapp.models_api.entity.User;
import com.modelsapp.models_api.permission.EnumPermission;
import com.modelsapp.models_api.service.UserService;

@SpringBootApplication
public class ModelsApiApplication implements CommandLineRunner {

	@Autowired
	private UserService userService = new UserService();

	public static void main(String[] args) {
		SpringApplication.run(ModelsApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		User user = new User();
		user.setUsername("admim");
		user.setPassword("admin");

		List<Role> roles = new ArrayList<>();
		Role roleAdmin = new Role();
		roleAdmin.setName(EnumPermission.ADMINISTRADOR);
		/*
		 * Role roleuser = new Role();
		 * roleuser.setName(EnumPermission.USUARIO);
		 */
		roles.add(roleAdmin);
		/* roles.add(roleuser); */
		user.setRoles(roles);

		userService.salvarUsuario(user);
		//TEORICAMENTE SÓ SE DEVE RODAR UMA VEZ ISSO(!!!!)
	}
}
