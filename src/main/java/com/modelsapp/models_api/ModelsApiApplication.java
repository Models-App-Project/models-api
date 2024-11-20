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
public class ModelsApiApplication{

	@Autowired
	private UserService userService = new UserService();

	public static void main(String[] args) {
		SpringApplication.run(ModelsApiApplication.class, args);
	}
}
