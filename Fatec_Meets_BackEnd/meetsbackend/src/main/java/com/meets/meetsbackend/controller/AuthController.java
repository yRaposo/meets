package com.meets.meetsbackend.controller;

import com.meets.meetsbackend.dto.LoginRequest;
import com.meets.meetsbackend.dto.LoginResponse;
import com.meets.meetsbackend.dto.UsuarioResponse;
import com.meets.meetsbackend.model.Usuario;
import com.meets.meetsbackend.repository.UsuarioRepository;
import com.meets.meetsbackend.security.JwtUtil;
import com.meets.meetsbackend.service.UsuarioService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller para autenticacao (login).
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final UsuarioRepository usuarioRepository;
    private final UsuarioService usuarioService;
    private final JwtUtil jwtUtil;

    public AuthController(UsuarioRepository usuarioRepository, UsuarioService usuarioService, JwtUtil jwtUtil) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioService = usuarioService;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Endpoint de login.
     * POST /api/auth/login
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        log.debug("POST /api/auth/login - Tentativa de login: {}", request.getEmail());

        // Buscar usuario por email
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Email ou senha invalidos"));

        // Validar senha
        if (!usuarioService.validarSenha(usuario, request.getSenha())) {
            log.warn("Tentativa de login com senha invalida: {}", request.getEmail());
            throw new IllegalArgumentException("Email ou senha invalidos");
        }

        // Gerar token JWT
        String token = jwtUtil.generateToken(usuario.getEmail());

        // Criar resposta
        UsuarioResponse usuarioResponse = new UsuarioResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail()
        );

        LoginResponse response = new LoginResponse(token, usuarioResponse);

        log.info("Login bem-sucedido: {}", request.getEmail());
        return ResponseEntity.ok(response);
    }
}
