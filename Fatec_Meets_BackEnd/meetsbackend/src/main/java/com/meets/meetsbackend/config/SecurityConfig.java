package com.meets.meetsbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuração de segurança para desenvolvimento.
 * IMPORTANTE: Esta configuração DESABILITA a autenticação para facilitar
 * testes.
 * Em produção, implemente autenticação adequada (JWT, OAuth2, etc).
 * 
 * Endpoints publicos:
 * - POST /api/auth/login - Login para obter token JWT
 * - POST /api/usuarios - Registro de novo usuario
 * - GET /api/test - Teste de API
 * 
 * Para habilitar autenticacao JWT, descomente a configuracao de filtro abaixo.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Desabilita CSRF para facilitar testes com JSON
                .authorizeHttpRequests(auth -> auth
                        // Endpoints publicos (sem autenticacao)
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/test/**").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()
                        // Permite POST /api/usuarios para registro
                        .requestMatchers("POST", "/api/usuarios").permitAll()
                        // Todos os outros endpoints permitem por enquanto (dev mode)
                        .anyRequest().permitAll());

        return http.build();
    }
}
