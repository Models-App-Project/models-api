package com.modelsapp.models_api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.modelsapp.models_api.filter.AuthenticationFilter;
import com.modelsapp.models_api.filter.LoginFilter;
import com.modelsapp.models_api.permission.EnumPermission;
import com.modelsapp.models_api.service.AuthenticatedUserService;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private AuthenticatedUserService usuarioAutenticadoService;

    // Configuração do encoder de senha
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.csrf(crsf -> crsf.disable())
                .authorizeHttpRequests(auth -> {
                    //TODO REVISAR AS PERMISSÕES E OS ENDPOINTS
                    auth.requestMatchers("/users/register", "/users/login").permitAll()
                            .requestMatchers(HttpMethod.GET, "/models").hasAuthority(EnumPermission.ADMINISTRADOR.toString())
                            .requestMatchers(HttpMethod.GET, "/usuario").hasAuthority(EnumPermission.USUARIO.toString())
                            .requestMatchers(HttpMethod.POST, "/NSEI").hasAuthority(EnumPermission.MODEL.toString())
                            .requestMatchers(HttpMethod.POST, "/models").hasAuthority(EnumPermission.ADMINISTRADOR.toString())
                            .anyRequest()
                            .authenticated();
                });

        httpSecurity.addFilterBefore(new LoginFilter("/users/login", authenticationConfiguration.getAuthenticationManager()), UsernamePasswordAuthenticationFilter.class);
        httpSecurity.addFilterBefore(new AuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

}
