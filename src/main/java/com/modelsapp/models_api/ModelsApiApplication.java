package com.modelsapp.models_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.modelsapp.models_api.service.UserService;

@SpringBootApplication
public class ModelsApiApplication{

	@Autowired
	private UserService userService = new UserService();

	public static void main(String[] args) {
		SpringApplication.run(ModelsApiApplication.class, args);
	}
}
