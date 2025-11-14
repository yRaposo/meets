package com.meets.meetsbackend.controller;

import com.meets.meetsbackend.model.Instituicao;
import com.meets.meetsbackend.service.InstituicaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/instituicoes")
public class InstituicaoController {

    private final InstituicaoService instituicaoService;

    public InstituicaoController(InstituicaoService instituicaoService) {
        this.instituicaoService = instituicaoService;
    }

    @GetMapping
    public ResponseEntity<List<Instituicao>> listar() { return ResponseEntity.ok(instituicaoService.listarTodos()); }

    @GetMapping("/{id}")
    public ResponseEntity<Instituicao> buscar(@PathVariable Long id) {
        return instituicaoService.buscarPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Instituicao> criar(@RequestBody Instituicao i) {
        Instituicao criado = instituicaoService.criar(i);
        return ResponseEntity.created(URI.create("/api/instituicoes/" + criado.getId())).body(criado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Instituicao> atualizar(@PathVariable Long id, @RequestBody Instituicao dados) {
        return instituicaoService.atualizar(id, dados).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        instituicaoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
