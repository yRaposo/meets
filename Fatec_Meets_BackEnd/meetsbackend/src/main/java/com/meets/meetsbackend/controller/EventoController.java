package com.meets.meetsbackend.controller;

import com.meets.meetsbackend.model.Evento;
import com.meets.meetsbackend.service.EventoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/eventos")
public class EventoController {

    private static final Logger logger = LoggerFactory.getLogger(EventoController.class);
    private final EventoService eventoService;

    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @GetMapping
    public ResponseEntity<List<Evento>> listar() {
        logger.info("GET /api/eventos - Listando todos os eventos");
        List<Evento> eventos = eventoService.listarTodos();
        logger.info("GET /api/eventos - {} eventos encontrados", eventos.size());
        return ResponseEntity.ok(eventos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Evento> buscar(@PathVariable Long id) {
        logger.info("GET /api/eventos/{} - Buscando evento", id);
        return eventoService.buscarPorId(id)
                .map(evento -> {
                    logger.info("GET /api/eventos/{} - Evento encontrado", id);
                    return ResponseEntity.ok(evento);
                })
                .orElseGet(() -> {
                    logger.warn("GET /api/eventos/{} - Evento não encontrado", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PostMapping
    public ResponseEntity<Evento> criar(@RequestBody Evento e) {
        logger.info("POST /api/eventos - Criando novo evento");
        Evento criado = eventoService.criar(e);
        logger.info("POST /api/eventos - Evento criado com ID: {}", criado.getId());
        return ResponseEntity.created(URI.create("/api/eventos/" + criado.getId())).body(criado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Evento> atualizar(@PathVariable Long id, @RequestBody Evento dados) {
        logger.info("PUT /api/eventos/{} - Atualizando evento", id);
        return eventoService.atualizar(id, dados)
                .map(evento -> {
                    logger.info("PUT /api/eventos/{} - Evento atualizado", id);
                    return ResponseEntity.ok(evento);
                })
                .orElseGet(() -> {
                    logger.warn("PUT /api/eventos/{} - Evento não encontrado", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        logger.info("DELETE /api/eventos/{} - Excluindo evento", id);
        eventoService.excluir(id);
        logger.info("DELETE /api/eventos/{} - Evento excluído", id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Adiciona um participante ao evento
     * POST /api/eventos/{id}/participar?usuarioId=123
     */
    @PostMapping("/{id}/participar")
    public ResponseEntity<Evento> adicionarParticipante(@PathVariable Long id, @RequestParam Long usuarioId) {
        logger.info("POST /api/eventos/{}/participar - Adicionando participante: usuário {}", id, usuarioId);
        return eventoService.adicionarParticipante(id, usuarioId)
                .map(evento -> {
                    logger.info("POST /api/eventos/{}/participar - Participante adicionado com sucesso", id);
                    return ResponseEntity.ok(evento);
                })
                .orElseGet(() -> {
                    logger.warn("POST /api/eventos/{}/participar - Falha ao adicionar participante (evento lotado, evento não encontrado ou usuário não encontrado)", id);
                    return ResponseEntity.notFound().build();
                });
    }

    /**
     * Remove um participante do evento
     * DELETE /api/eventos/{id}/participar?usuarioId=123
     */
    @DeleteMapping("/{id}/participar")
    public ResponseEntity<Evento> removerParticipante(@PathVariable Long id, @RequestParam Long usuarioId) {
        logger.info("DELETE /api/eventos/{}/participar - Removendo participante: usuário {}", id, usuarioId);
        return eventoService.removerParticipante(id, usuarioId)
                .map(evento -> {
                    logger.info("DELETE /api/eventos/{}/participar - Participante removido com sucesso", id);
                    return ResponseEntity.ok(evento);
                })
                .orElseGet(() -> {
                    logger.warn("DELETE /api/eventos/{}/participar - Falha ao remover participante (evento ou usuário não encontrado)", id);
                    return ResponseEntity.notFound().build();
                });
    }
}
