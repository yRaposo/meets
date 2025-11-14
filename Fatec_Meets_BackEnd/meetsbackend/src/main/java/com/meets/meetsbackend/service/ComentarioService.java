package com.meets.meetsbackend.service;

import com.meets.meetsbackend.model.Comentario;
import com.meets.meetsbackend.model.Postagem;
import com.meets.meetsbackend.model.Usuario;
import com.meets.meetsbackend.repository.ComentarioRepository;
import com.meets.meetsbackend.repository.PostagemRepository;
import com.meets.meetsbackend.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ComentarioService {

    private final ComentarioRepository comentarioRepository;
    private final PostagemRepository postagemRepository;
    private final UsuarioRepository usuarioRepository;

    public ComentarioService(ComentarioRepository comentarioRepository, PostagemRepository postagemRepository, UsuarioRepository usuarioRepository) {
        this.comentarioRepository = comentarioRepository;
        this.postagemRepository = postagemRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<Comentario> listarTodos() { return comentarioRepository.findAll(); }

    public Optional<Comentario> buscarPorId(Long id) { return comentarioRepository.findById(id); }

    public Comentario criar(Comentario c) {
        if (c.getPostagem() != null && c.getPostagem().getId() != null) {
            Postagem p = postagemRepository.findById(c.getPostagem().getId()).orElse(null);
            c.setPostagem(p);
        }
        if (c.getUsuario() != null && c.getUsuario().getId() != null) {
            Usuario u = usuarioRepository.findById(c.getUsuario().getId()).orElse(null);
            c.setUsuario(u);
        }
        return comentarioRepository.save(c);
    }

    public void excluir(Long id) { comentarioRepository.deleteById(id); }
}
