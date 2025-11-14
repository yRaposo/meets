package com.meets.meetsbackend.service;

import com.meets.meetsbackend.model.Denuncia;
import com.meets.meetsbackend.model.Postagem;
import com.meets.meetsbackend.model.Usuario;
import com.meets.meetsbackend.repository.DenunciaRepository;
import com.meets.meetsbackend.repository.PostagemRepository;
import com.meets.meetsbackend.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DenunciaService {

    private final DenunciaRepository denunciaRepository;
    private final PostagemRepository postagemRepository;
    private final UsuarioRepository usuarioRepository;

    public DenunciaService(DenunciaRepository denunciaRepository, PostagemRepository postagemRepository, UsuarioRepository usuarioRepository) {
        this.denunciaRepository = denunciaRepository;
        this.postagemRepository = postagemRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<Denuncia> listarTodos() { return denunciaRepository.findAll(); }
    public Optional<Denuncia> buscarPorId(Long id) { return denunciaRepository.findById(id); }

    public Denuncia criar(Denuncia d) {
        if (d.getPostagem() != null && d.getPostagem().getId() != null) {
            Postagem p = postagemRepository.findById(d.getPostagem().getId()).orElse(null);
            d.setPostagem(p);
        }
        if (d.getDenunciante() != null && d.getDenunciante().getId() != null) {
            Usuario u = usuarioRepository.findById(d.getDenunciante().getId()).orElse(null);
            d.setDenunciante(u);
        }
        return denunciaRepository.save(d);
    }

    public void excluir(Long id) { denunciaRepository.deleteById(id); }
}
