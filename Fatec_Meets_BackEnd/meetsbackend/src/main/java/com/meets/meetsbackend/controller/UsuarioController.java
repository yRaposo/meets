package com.meets.meetsbackend.controller;

import com.meets.meetsbackend.model.Usuario;
import com.meets.meetsbackend.service.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

/**
 * Controller REST para gerenciamento de usuários.
 * Endpoints oferecem CRUD básico. Em produção, adicione validações,
 * autenticação e DTOs para entrada/saída.
 */
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private static final Logger log = LoggerFactory.getLogger(UsuarioController.class);
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /**
     * Lista todos os usuários com paginação opcional.
     * Exemplo: GET /api/usuarios?page=0&size=10&sort=nome,asc
     */
    @GetMapping
    public ResponseEntity<?> listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "false") boolean paginado) {

        log.debug("GET /api/usuarios - Listando usuarios (paginado={})", paginado);

        if (paginado) {
            Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
            Page<Usuario> paginaUsuarios = usuarioService.listarTodosPaginado(pageable);
            return ResponseEntity.ok(paginaUsuarios);
        } else {
            return ResponseEntity.ok(usuarioService.listarTodos());
        }
    }

    /** Busca um usuário por id */
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscar(@PathVariable Long id) {
        log.debug("GET /api/usuarios/{} - Buscando usuario", id);
        return usuarioService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /** Cria um novo usuário */
    @PostMapping
    public ResponseEntity<Usuario> criar(@RequestBody Usuario usuario) {
        log.debug("POST /api/usuarios - Criando usuario: nome={}, email={}",
                usuario != null ? usuario.getNome() : "null",
                usuario != null ? usuario.getEmail() : "null");

        if (usuario == null) {
            log.error("Usuario recebido e null");
            throw new IllegalArgumentException("Corpo da requisicao nao pode ser vazio");
        }

        try {
            Usuario criado = usuarioService.criar(usuario);
            log.info("Controller: Usuario criado com sucesso: ID={}", criado.getId());
            return ResponseEntity.created(URI.create("/api/usuarios/" + criado.getId())).body(criado);
        } catch (Exception e) {
            log.error("Controller: Erro ao criar usuario: {}", e.getMessage(), e);
            throw e;
        }
    }

    /** Atualiza um usuário existente */
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizar(@PathVariable Long id, @RequestBody Usuario usuario) {
        return usuarioService.atualizar(id, usuario)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /** Remove um usuário */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        usuarioService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
