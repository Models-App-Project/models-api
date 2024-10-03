package com.modelsapp.models_api.filter;

import java.io.IOException;
import java.util.Collections;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.modelsapp.models_api.entity.AuthenticatedUser;
import com.modelsapp.models_api.service.AuthenticationService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoginFilter extends AbstractAuthenticationProcessingFilter {

    public LoginFilter(String url, AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        String collect = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        AuthenticatedUser usuarioAutenticado = new ObjectMapper().readValue(collect, AuthenticatedUser.class);

        return getAuthenticationManager()
                .authenticate(new UsernamePasswordAuthenticationToken(
                        usuarioAutenticado.getUsername(),
                        usuarioAutenticado.getPassword(),
                        Collections.emptyList()));
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest HttpServletRequest,
            HttpServletResponse httpServletResponse,
            FilterChain filterChain,
            Authentication authentication) {
        AuthenticationService.addJWTToken(httpServletResponse, authentication);
    }

}