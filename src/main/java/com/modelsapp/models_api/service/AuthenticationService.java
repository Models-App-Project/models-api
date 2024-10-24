package com.modelsapp.models_api.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;
import java.util.stream.Collectors;

public class AuthenticationService {

    private static final String HEADER_AUTHORIZATION = "Authorization";
    private static final String JWT_KEY = "signinKey";
    private static final String AUTHORITIES = "authorities";
    private static final int EXPIRATION_TOKEN_ONE_HOUR = 3600000;
    private static final String BEARER = "Bearer";

    static public void addJWTToken(HttpServletResponse response, Authentication authentication) {

        Map<String, Object> claims = new HashMap<>();

        claims.put(AUTHORITIES, authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));

        @SuppressWarnings("deprecation")
        String jwtToken = Jwts.builder()
                .setSubject(authentication.getName())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TOKEN_ONE_HOUR))
                .signWith(SignatureAlgorithm.HS512, JWT_KEY)
                .addClaims(claims)
                .compact();
        response.addHeader(HEADER_AUTHORIZATION, BEARER + " " + jwtToken);
        response.addHeader("Access-Control-Expode-Headers", HEADER_AUTHORIZATION);
    }

    static public Authentication obterAutenticacao(HttpServletRequest request) {
        String token = request.getHeader(HEADER_AUTHORIZATION);

        if (token != null) {
            @SuppressWarnings("deprecation")
            Claims user = Jwts.parser()
                    .setSigningKey(JWT_KEY)
                    .parseClaimsJws(token.replace(BEARER + " ", ""))
                    .getBody();
            if (user != null) {

                @SuppressWarnings("unchecked")
                List<SimpleGrantedAuthority> permissoes = ((ArrayList<String>) user.get(AUTHORITIES))
                        .stream()
                        .map(SimpleGrantedAuthority::new)
                        .toList();

                return new UsernamePasswordAuthenticationToken(user, null, permissoes);
            } else {
                throw new RuntimeException("Autenticação falhou!");
            }
        }
        return null;
    }

}