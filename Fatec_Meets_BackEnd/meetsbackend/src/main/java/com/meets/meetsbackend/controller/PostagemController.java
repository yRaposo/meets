package com.meets.meetsbackend.controller;

import com.meets.meetsbackend.model.Postagem;
import com.meets.meetsbackend.service.PostagemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/postagens")
public class PostagemController {

    private static final Logger logger = LoggerFactory.getLogger(PostagemController.class);
    private final PostagemService postagemService;

    public PostagemController(PostagemService postagemService) {
        this.postagemService = postagemService;
    }

    @GetMapping
    public ResponseEntity<List<Postagem>> listar() { 
        logger.info("GET /api/postagens - Listando todas as postagens");
        List<Postagem> postagens = postagemService.listarTodos();
        logger.info("GET /api/postagens - {} postagens encontradas", postagens.size());
        return ResponseEntity.ok(postagens);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Postagem> buscar(@PathVariable Long id) {
        logger.info("GET /api/postagens/{} - Buscando postagem", id);
        return postagemService.buscarPorId(id)
                .map(postagem -> {
                    logger.info("GET /api/postagens/{} - Postagem encontrada", id);
                    return ResponseEntity.ok(postagem);
                })
                .orElseGet(() -> {
                    logger.warn("GET /api/postagens/{} - Postagem não encontrada", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PostMapping
    public ResponseEntity<Postagem> criar(@RequestBody Postagem p) {
        logger.info("POST /api/postagens - Criando nova postagem");
        Postagem criado = postagemService.criar(p);
        logger.info("POST /api/postagens - Postagem criada com ID: {}", criado.getId());
        return ResponseEntity.created(URI.create("/api/postagens/" + criado.getId())).body(criado);
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Postagem> criarComImagens(
            @RequestParam("titulo") String titulo,
            @RequestParam("conteudo") String conteudo,
            @RequestParam("usuarioId") Long usuarioId,
            @RequestParam(value = "imagens", required = false) List<MultipartFile> imagens,
            @RequestParam(value = "isEvento", required = false) Boolean isEvento,
            @RequestParam(value = "dataEvento", required = false) String dataEvento,
            @RequestParam(value = "horarioInicio", required = false) String horarioInicio,
            @RequestParam(value = "horarioFim", required = false) String horarioFim,
            @RequestParam(value = "local", required = false) String local,
            @RequestParam(value = "endereco", required = false) String endereco,
            @RequestParam(value = "capacidadeMaxima", required = false) Integer capacidadeMaxima) {
        
        logger.info("POST /api/postagens (multipart) - Criando postagem {} com {} imagem(ns)", 
                    isEvento != null && isEvento ? "(EVENTO)" : "",
                    imagens != null ? imagens.size() : 0);
        
        Postagem criado;
        if (isEvento != null && isEvento) {
            // Criar postagem com evento
            criado = postagemService.criarComImagensEEvento(
                titulo, conteudo, usuarioId, imagens,
                dataEvento, horarioInicio, horarioFim, local, endereco, capacidadeMaxima
            );
            logger.info("POST /api/postagens (multipart) - EVENTO criado com ID: {}", criado.getId());
        } else {
            // Criar postagem normal com imagens
            criado = postagemService.criarComImagens(titulo, conteudo, usuarioId, imagens);
            logger.info("POST /api/postagens (multipart) - Postagem criada com ID: {}", criado.getId());
        }
        
        return ResponseEntity.created(URI.create("/api/postagens/" + criado.getId())).body(criado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Postagem> atualizar(@PathVariable Long id, @RequestBody Postagem dados) {
        logger.info("PUT /api/postagens/{} - Atualizando postagem", id);
        return postagemService.atualizar(id, dados)
                .map(postagem -> {
                    logger.info("PUT /api/postagens/{} - Postagem atualizada", id);
                    return ResponseEntity.ok(postagem);
                })
                .orElseGet(() -> {
                    logger.warn("PUT /api/postagens/{} - Postagem não encontrada", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        logger.info("DELETE /api/postagens/{} - Excluindo postagem", id);
        postagemService.excluir(id);
        logger.info("DELETE /api/postagens/{} - Postagem excluída", id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<Postagem> adicionarLike(@PathVariable Long id, @RequestParam Long usuarioId) {
        logger.info("POST /api/postagens/{}/like - Adicionando like do usuário {}", id, usuarioId);
        return postagemService.adicionarLike(id, usuarioId)
                .map(postagem -> {
                    logger.info("POST /api/postagens/{}/like - Like adicionado com sucesso para usuário {}", id, usuarioId);
                    return ResponseEntity.ok(postagem);
                })
                .orElseGet(() -> {
                    logger.warn("POST /api/postagens/{}/like - Postagem ou usuário não encontrado (postagem: {}, usuário: {})", id, id, usuarioId);
                    return ResponseEntity.notFound().build();
                });
    }

    @DeleteMapping("/{id}/like")
    public ResponseEntity<Postagem> removerLike(@PathVariable Long id, @RequestParam Long usuarioId) {
        logger.info("DELETE /api/postagens/{}/like - Removendo like do usuário {}", id, usuarioId);
        return postagemService.removerLike(id, usuarioId)
                .map(postagem -> {
                    logger.info("DELETE /api/postagens/{}/like - Like removido com sucesso para usuário {}", id, usuarioId);
                    return ResponseEntity.ok(postagem);
                })
                .orElseGet(() -> {
                    logger.warn("DELETE /api/postagens/{}/like - Postagem ou usuário não encontrado (postagem: {}, usuário: {})", id, id, usuarioId);
                    return ResponseEntity.notFound().build();
                });
    }
}
