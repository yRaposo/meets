package com.meets.meetsbackend.controller;

import com.meets.meetsbackend.model.Denuncia;
import com.meets.meetsbackend.service.DenunciaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/denuncias")
public class DenunciaController {

    private final DenunciaService denunciaService;

    public DenunciaController(DenunciaService denunciaService) {
        this.denunciaService = denunciaService;
    }

    @GetMapping
    public ResponseEntity<List<Denuncia>> listar() { return ResponseEntity.ok(denunciaService.listarTodos()); }

    @GetMapping("/{id}")
    public ResponseEntity<Denuncia> buscar(@PathVariable Long id) {
        return denunciaService.buscarPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Denuncia> criar(@RequestBody Denuncia d) {
        Denuncia criado = denunciaService.criar(d);
        return ResponseEntity.created(URI.create("/api/denuncias/" + criado.getId())).body(criado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        denunciaService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
