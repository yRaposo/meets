package com.meets.meetsbackend.service;

import com.meets.meetsbackend.model.Evento;
import com.meets.meetsbackend.model.Imagem;
import com.meets.meetsbackend.model.Postagem;
import com.meets.meetsbackend.model.Usuario;
import com.meets.meetsbackend.repository.EventoRepository;
import com.meets.meetsbackend.repository.ImagemRepository;
import com.meets.meetsbackend.repository.PostagemRepository;
import com.meets.meetsbackend.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class PostagemService {

    private static final Logger logger = LoggerFactory.getLogger(PostagemService.class);
    private static final String UPLOAD_DIR = "uploads/postagens/";
    
    @Value("${app.base-url:http://10.0.2.2:8080}")
    private String baseUrl;
    
    private final PostagemRepository postagemRepository;
    private final UsuarioRepository usuarioRepository;
    private final ImagemRepository imagemRepository;
    private final EventoRepository eventoRepository;

    public PostagemService(PostagemRepository postagemRepository, UsuarioRepository usuarioRepository, 
                          ImagemRepository imagemRepository, EventoRepository eventoRepository) {
        this.postagemRepository = postagemRepository;
        this.usuarioRepository = usuarioRepository;
        this.imagemRepository = imagemRepository;
        this.eventoRepository = eventoRepository;
        
        // Cria diretório de uploads se não existir
        try {
            Files.createDirectories(Paths.get(UPLOAD_DIR));
        } catch (IOException e) {
            logger.error("Erro ao criar diretório de uploads", e);
        }
    }

    public List<Postagem> listarTodos() { 
        logger.debug("Service: Listando todas as postagens");
        List<Postagem> postagens = postagemRepository.findAll();
        logger.debug("Service: {} postagens encontradas no banco", postagens.size());
        return postagens;
    }

    public Optional<Postagem> buscarPorId(Long id) { 
        logger.debug("Service: Buscando postagem com ID: {}", id);
        Optional<Postagem> postagem = postagemRepository.findById(id);
        if (postagem.isPresent()) {
            logger.debug("Service: Postagem {} encontrada", id);
        } else {
            logger.warn("Service: Postagem {} não encontrada no banco", id);
        }
        return postagem;
    }

    public Postagem criar(Postagem p) {
        logger.debug("Service: Criando nova postagem");
        // Assegura que usuario exista antes de salvar
        if (p.getUsuario() != null && p.getUsuario().getId() != null) {
            Usuario u = usuarioRepository.findById(p.getUsuario().getId()).orElse(null);
            p.setUsuario(u);
            logger.debug("Service: Usuário {} associado à postagem", u != null ? u.getId() : "null");
        }
        Postagem salva = postagemRepository.save(p);
        logger.info("Service: Postagem criada com ID: {}", salva.getId());
        return salva;
    }

    public Optional<Postagem> atualizar(Long id, Postagem dados) {
        logger.debug("Service: Atualizando postagem com ID: {}", id);
        return postagemRepository.findById(id).map(p -> {
            p.setTitulo(dados.getTitulo());
            p.setConteudo(dados.getConteudo());
            Postagem atualizada = postagemRepository.save(p);
            logger.info("Service: Postagem {} atualizada", id);
            return atualizada;
        });
    }

    public void excluir(Long id) { 
        logger.debug("Service: Excluindo postagem com ID: {}", id);
        postagemRepository.deleteById(id);
        logger.info("Service: Postagem {} excluída", id);
    }

    public Optional<Postagem> adicionarLike(Long postagemId, Long usuarioId) {
        logger.debug("Service: Adicionando like - Postagem: {}, Usuário: {}", postagemId, usuarioId);
        
        Optional<Postagem> postagemOpt = postagemRepository.findById(postagemId);
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);

        if (!postagemOpt.isPresent()) {
            logger.warn("Service: Postagem {} não encontrada ao adicionar like", postagemId);
            return Optional.empty();
        }
        
        if (!usuarioOpt.isPresent()) {
            logger.warn("Service: Usuário {} não encontrado ao adicionar like", usuarioId);
            return Optional.empty();
        }

        Postagem postagem = postagemOpt.get();
        Usuario usuario = usuarioOpt.get();
        
        logger.debug("Service: Postagem e usuário encontrados, adicionando like...");
        postagem.adicionarLike(usuario);
        Postagem salva = postagemRepository.save(postagem);
        logger.info("Service: Like adicionado com sucesso - Postagem: {}, Usuário: {}, Total de likes: {}", 
                postagemId, usuarioId, salva.getLikes().size());
        return Optional.of(salva);
    }

    public Optional<Postagem> removerLike(Long postagemId, Long usuarioId) {
        logger.debug("Service: Removendo like - Postagem: {}, Usuário: {}", postagemId, usuarioId);
        
        Optional<Postagem> postagemOpt = postagemRepository.findById(postagemId);
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);

        if (!postagemOpt.isPresent()) {
            logger.warn("Service: Postagem {} não encontrada ao remover like", postagemId);
            return Optional.empty();
        }
        
        if (!usuarioOpt.isPresent()) {
            logger.warn("Service: Usuário {} não encontrado ao remover like", usuarioId);
            return Optional.empty();
        }

        Postagem postagem = postagemOpt.get();
        Usuario usuario = usuarioOpt.get();
        
        logger.debug("Service: Postagem e usuário encontrados, removendo like...");
        postagem.removerLike(usuario);
        Postagem salva = postagemRepository.save(postagem);
        logger.info("Service: Like removido com sucesso - Postagem: {}, Usuário: {}, Total de likes: {}", 
                postagemId, usuarioId, salva.getLikes().size());
        return Optional.of(salva);
    }

    public Postagem criarComImagens(String titulo, String conteudo, Long usuarioId, List<MultipartFile> imagens) {
        logger.debug("Service: Criando postagem com imagens - Usuário: {}", usuarioId);
        
        // Busca o usuário
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado: " + usuarioId));
        
        // Cria a postagem
        Postagem postagem = new Postagem(titulo, conteudo, usuario);
        Postagem postagemSalva = postagemRepository.save(postagem);
        logger.info("Service: Postagem criada com ID: {}", postagemSalva.getId());
        
        // Processa as imagens se houver
        if (imagens != null && !imagens.isEmpty()) {
            logger.debug("Service: Processando {} imagem(ns)", imagens.size());
            logger.info("Service: Base URL configurada: {}", baseUrl);
            int ordem = 0;
            for (MultipartFile arquivo : imagens) {
                try {
                    String nomeArquivo = salvarArquivo(arquivo);
                    // Usa baseUrl configurável do application.properties
                    String url = baseUrl + "/uploads/postagens/" + nomeArquivo;
                    
                    Imagem imagem = new Imagem(url, arquivo.getOriginalFilename(), postagemSalva, ordem++);
                    imagemRepository.save(imagem);
                    logger.info("Service: Imagem salva - {}", url);
                } catch (IOException e) {
                    logger.error("Service: Erro ao salvar imagem", e);
                }
            }
        }
        
        // Retorna a postagem com as imagens
        return postagemRepository.findById(postagemSalva.getId()).orElse(postagemSalva);
    }

    public Postagem criarComImagensEEvento(String titulo, String conteudo, Long usuarioId, 
                                           List<MultipartFile> imagens,
                                           String dataEvento, String horarioInicio, String horarioFim,
                                           String local, String endereco, Integer capacidadeMaxima) {
        logger.debug("Service: Criando postagem com evento - Usuário: {}", usuarioId);
        
        // Busca o usuário
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado: " + usuarioId));
        
        // Cria a postagem
        Postagem postagem = new Postagem(titulo, conteudo, usuario);
        Postagem postagemSalva = postagemRepository.save(postagem);
        logger.info("Service: Postagem (EVENTO) criada com ID: {}", postagemSalva.getId());
        
        // Processa as imagens se houver
        if (imagens != null && !imagens.isEmpty()) {
            logger.debug("Service: Processando {} imagem(ns) do evento", imagens.size());
            logger.info("Service: Base URL configurada: {}", baseUrl);
            int ordem = 0;
            for (MultipartFile arquivo : imagens) {
                try {
                    String nomeArquivo = salvarArquivo(arquivo);
                    String url = baseUrl + "/uploads/postagens/" + nomeArquivo;
                    
                    Imagem imagem = new Imagem(url, arquivo.getOriginalFilename(), postagemSalva, ordem++);
                    imagemRepository.save(imagem);
                    logger.info("Service: Imagem do evento salva - {}", url);
                } catch (IOException e) {
                    logger.error("Service: Erro ao salvar imagem do evento", e);
                }
            }
        }
        
        // Cria o evento
        try {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            
            LocalDate data = LocalDate.parse(dataEvento, dateFormatter);
            LocalTime horaInicio = LocalTime.parse(horarioInicio, timeFormatter);
            LocalTime horaFim = horarioFim != null && !horarioFim.trim().isEmpty() 
                    ? LocalTime.parse(horarioFim, timeFormatter) 
                    : null;
            
            Evento evento = new Evento(
                titulo,
                conteudo,
                data,
                horaInicio,
                horaFim,
                local,
                endereco,
                capacidadeMaxima,
                usuario,
                postagemSalva
            );
            
            eventoRepository.save(evento);
            logger.info("Service: Evento criado e vinculado à postagem {}", postagemSalva.getId());
            
        } catch (Exception e) {
            logger.error("Service: Erro ao criar evento", e);
            throw new RuntimeException("Erro ao criar evento: " + e.getMessage(), e);
        }
        
        // Retorna a postagem com as imagens e evento
        return postagemRepository.findById(postagemSalva.getId()).orElse(postagemSalva);
    }

    private String salvarArquivo(MultipartFile arquivo) throws IOException {
        String nomeOriginal = arquivo.getOriginalFilename();
        String extensao = nomeOriginal != null && nomeOriginal.contains(".") 
                ? nomeOriginal.substring(nomeOriginal.lastIndexOf(".")) 
                : "";
        String nomeArquivo = UUID.randomUUID().toString() + extensao;
        
        Path caminho = Paths.get(UPLOAD_DIR + nomeArquivo);
        Files.copy(arquivo.getInputStream(), caminho, StandardCopyOption.REPLACE_EXISTING);
        
        logger.debug("Service: Arquivo salvo - {}", nomeArquivo);
        return nomeArquivo;
    }
}
