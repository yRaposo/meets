package com.meets.meetsbackend.service;

import com.meets.meetsbackend.model.Evento;
import com.meets.meetsbackend.model.Usuario;
import com.meets.meetsbackend.repository.EventoRepository;
import com.meets.meetsbackend.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EventoService {

    private static final Logger logger = LoggerFactory.getLogger(EventoService.class);
    private final EventoRepository eventoRepository;
    private final UsuarioRepository usuarioRepository;

    public EventoService(EventoRepository eventoRepository, UsuarioRepository usuarioRepository) {
        this.eventoRepository = eventoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<Evento> listarTodos() {
        logger.debug("Service: Listando todos os eventos");
        List<Evento> eventos = eventoRepository.findAll();
        logger.debug("Service: {} eventos encontrados no banco", eventos.size());
        return eventos;
    }

    public Optional<Evento> buscarPorId(Long id) {
        logger.debug("Service: Buscando evento com ID: {}", id);
        Optional<Evento> evento = eventoRepository.findById(id);
        if (evento.isPresent()) {
            logger.debug("Service: Evento {} encontrado", id);
        } else {
            logger.warn("Service: Evento {} não encontrado no banco", id);
        }
        return evento;
    }

    public Evento criar(Evento e) {
        logger.debug("Service: Criando novo evento");
        Evento salvo = eventoRepository.save(e);
        logger.info("Service: Evento criado com ID: {}", salvo.getId());
        return salvo;
    }

    public Optional<Evento> atualizar(Long id, Evento dados) {
        logger.debug("Service: Atualizando evento com ID: {}", id);
        return eventoRepository.findById(id).map(ev -> {
            ev.setTitulo(dados.getTitulo());
            ev.setDescricao(dados.getDescricao());
            ev.setDataEvento(dados.getDataEvento());
            ev.setHorarioInicio(dados.getHorarioInicio());
            ev.setHorarioFim(dados.getHorarioFim());
            ev.setLocal(dados.getLocal());
            ev.setEndereco(dados.getEndereco());
            ev.setCapacidadeMaxima(dados.getCapacidadeMaxima());
            Evento atualizado = eventoRepository.save(ev);
            logger.info("Service: Evento {} atualizado", id);
            return atualizado;
        });
    }

    public void excluir(Long id) {
        logger.debug("Service: Excluindo evento com ID: {}", id);
        eventoRepository.deleteById(id);
        logger.info("Service: Evento {} excluído", id);
    }

    /**
     * Adiciona um participante ao evento
     */
    public Optional<Evento> adicionarParticipante(Long eventoId, Long usuarioId) {
        logger.debug("Service: Adicionando participante - Evento: {}, Usuário: {}", eventoId, usuarioId);
        
        Optional<Evento> eventoOpt = eventoRepository.findById(eventoId);
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);

        if (!eventoOpt.isPresent()) {
            logger.warn("Service: Evento {} não encontrado ao adicionar participante", eventoId);
            return Optional.empty();
        }
        
        if (!usuarioOpt.isPresent()) {
            logger.warn("Service: Usuário {} não encontrado ao adicionar participante", usuarioId);
            return Optional.empty();
        }

        Evento evento = eventoOpt.get();
        Usuario usuario = usuarioOpt.get();
        
        // Verifica se o evento está lotado
        if (evento.isLotado()) {
            logger.warn("Service: Evento {} está lotado (capacidade: {})", eventoId, evento.getCapacidadeMaxima());
            return Optional.empty();
        }
        
        logger.debug("Service: Evento e usuário encontrados, adicionando participante...");
        evento.adicionarParticipante(usuario);
        Evento salvo = eventoRepository.save(evento);
        logger.info("Service: Participante adicionado com sucesso - Evento: {}, Usuário: {}, Total de participantes: {}", 
                eventoId, usuarioId, salvo.getTotalParticipantes());
        return Optional.of(salvo);
    }

    /**
     * Remove um participante do evento
     */
    public Optional<Evento> removerParticipante(Long eventoId, Long usuarioId) {
        logger.debug("Service: Removendo participante - Evento: {}, Usuário: {}", eventoId, usuarioId);
        
        Optional<Evento> eventoOpt = eventoRepository.findById(eventoId);
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);

        if (!eventoOpt.isPresent()) {
            logger.warn("Service: Evento {} não encontrado ao remover participante", eventoId);
            return Optional.empty();
        }
        
        if (!usuarioOpt.isPresent()) {
            logger.warn("Service: Usuário {} não encontrado ao remover participante", usuarioId);
            return Optional.empty();
        }

        Evento evento = eventoOpt.get();
        Usuario usuario = usuarioOpt.get();
        
        logger.debug("Service: Evento e usuário encontrados, removendo participante...");
        evento.removerParticipante(usuario);
        Evento salvo = eventoRepository.save(evento);
        logger.info("Service: Participante removido com sucesso - Evento: {}, Usuário: {}, Total de participantes: {}", 
                eventoId, usuarioId, salvo.getTotalParticipantes());
        return Optional.of(salvo);
    }
}
