package com.meets.meetsbackend.service;

import com.meets.meetsbackend.model.Usuario;
import com.meets.meetsbackend.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service para operações de Usuario.
 * Encapsula lógica de negócio e chamadas ao repositório.
 */
@Service
@Transactional
public class UsuarioService {

    private static final Logger log = LoggerFactory.getLogger(UsuarioService.class);
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Usuario> listarTodos() {
        log.debug("Listando todos os usuarios");
        return usuarioRepository.findAll();
    }
    
    public Page<Usuario> listarTodosPaginado(Pageable pageable) {
        log.debug("Listando usuarios com paginacao: page={}, size={}", pageable.getPageNumber(), pageable.getPageSize());
        return usuarioRepository.findAll(pageable);
    }

    public Optional<Usuario> buscarPorId(Long id) {
        log.debug("Buscando usuario por ID: {}", id);
        return usuarioRepository.findById(id);
    }

    public Usuario criar(Usuario usuario) {
        log.debug("Criando novo usuario: {}", usuario.getEmail());
        
        // Validacoes basicas
        if (usuario.getNome() == null || usuario.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do usuario e obrigatorio");
        }
        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email do usuario e obrigatorio");
        }
        if (usuario.getSenha() == null || usuario.getSenha().trim().isEmpty()) {
            throw new IllegalArgumentException("Senha do usuario e obrigatoria");
        }
        
        // Validar comprimento minimo da senha
        if (usuario.getSenha().length() < 6) {
            throw new IllegalArgumentException("Senha deve ter no minimo 6 caracteres");
        }
        
        // Hash da senha antes de salvar
        String senhaHash = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaHash);
        
        Usuario salvo = usuarioRepository.save(usuario);
        log.info("Usuario criado com sucesso: ID={}", salvo.getId());
        return salvo;
    }

    public Optional<Usuario> atualizar(Long id, Usuario dados) {
        return usuarioRepository.findById(id).map(u -> {
            if (dados.getNome() != null) {
                u.setNome(dados.getNome());
            }
            if (dados.getEmail() != null) {
                u.setEmail(dados.getEmail());
            }
            if (dados.getBio() != null) {
                u.setBio(dados.getBio());
            }
            if (dados.getFotoPerfil() != null) {
                u.setFotoPerfil(dados.getFotoPerfil());
            }
            // Não alteramos senha automaticamente aqui
            log.info("Usuario atualizado: ID={}", u.getId());
            return usuarioRepository.save(u);
        });
    }

    public void excluir(Long id) {
        usuarioRepository.deleteById(id);
    }
    
    /**
     * Valida se uma senha corresponde ao hash armazenado.
     * Util para login/autenticacao.
     */
    public boolean validarSenha(Usuario usuario, String senhaPlaintext) {
        return passwordEncoder.matches(senhaPlaintext, usuario.getSenha());
    }
}
