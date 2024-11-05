package com.modelsapp.models_api.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.modelsapp.models_api.filter.AuthenticationFilter;
import com.modelsapp.models_api.filter.LoginFilter;
import com.modelsapp.models_api.permission.EnumPermission;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    // Configuração do encoder de senha

   @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.csrf(crsf -> crsf.disable())
                .authorizeHttpRequests(auth -> {
                    // TODO REVISAR AS PERMISSÕES E OS ENDPOINTS
                    auth.requestMatchers("/users/login").permitAll()
                            .requestMatchers("/*").hasAuthority(EnumPermission.ADMINISTRADOR.toString())
                            .anyRequest()
                            .authenticated();
                });

        httpSecurity.addFilterBefore(
                new LoginFilter("/users/login", authenticationConfiguration.getAuthenticationManager()),
                UsernamePasswordAuthenticationFilter.class);
        httpSecurity.addFilterBefore(new AuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }


}
