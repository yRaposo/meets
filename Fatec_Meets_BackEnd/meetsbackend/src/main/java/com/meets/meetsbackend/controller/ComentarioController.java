package com.meets.meetsbackend.controller;

import com.meets.meetsbackend.model.Comentario;
import com.meets.meetsbackend.service.ComentarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/comentarios")
public class ComentarioController {

    private final ComentarioService comentarioService;

    public ComentarioController(ComentarioService comentarioService) {
        this.comentarioService = comentarioService;
    }

    @GetMapping
    public ResponseEntity<List<Comentario>> listar() { return ResponseEntity.ok(comentarioService.listarTodos()); }

    @GetMapping("/{id}")
    public ResponseEntity<Comentario> buscar(@PathVariable Long id) {
        return comentarioService.buscarPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Comentario> criar(@RequestBody Comentario c) {
        Comentario criado = comentarioService.criar(c);
        return ResponseEntity.created(URI.create("/api/comentarios/" + criado.getId())).body(criado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        comentarioService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
