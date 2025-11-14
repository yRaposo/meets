package com.meets.meetsbackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidade Evento - para gerenciar eventos relacionados às postagens.
 * Todo evento DEVE ter um post associado.
 */
@Entity
@Table(name = "evento")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(nullable = false)
    private LocalDate dataEvento;

    @Column(nullable = false)
    private LocalTime horarioInicio;

    @Column
    private LocalTime horarioFim;

    @Column(length = 300)
    private String local;

    @Column(length = 500)
    private String endereco;

    @Column
    private Integer capacidadeMaxima;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id")
    private Usuario criador;

    /**
     * Lista de participantes confirmados no evento
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "evento_participantes",
        joinColumns = @JoinColumn(name = "evento_id"),
        inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    private List<Usuario> participantes = new ArrayList<>();

    /**
     * Todo evento deve ter um post associado (obrigatório)
     * As imagens do evento são as mesmas do post vinculado
     */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "postagem_id", nullable = false)
    @JsonBackReference
    private Postagem postagem;

    public Evento() {}

    public Evento(String titulo, String descricao, LocalDate dataEvento, LocalTime horarioInicio, 
                  LocalTime horarioFim, String local, String endereco, Integer capacidadeMaxima,
                  Usuario criador, Postagem postagem) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataEvento = dataEvento;
        this.horarioInicio = horarioInicio;
        this.horarioFim = horarioFim;
        this.local = local;
        this.endereco = endereco;
        this.capacidadeMaxima = capacidadeMaxima;
        this.criador = criador;
        this.postagem = postagem;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public LocalDate getDataEvento() { return dataEvento; }
    public void setDataEvento(LocalDate dataEvento) { this.dataEvento = dataEvento; }

    public LocalTime getHorarioInicio() { return horarioInicio; }
    public void setHorarioInicio(LocalTime horarioInicio) { this.horarioInicio = horarioInicio; }

    public LocalTime getHorarioFim() { return horarioFim; }
    public void setHorarioFim(LocalTime horarioFim) { this.horarioFim = horarioFim; }

    public String getLocal() { return local; }
    public void setLocal(String local) { this.local = local; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public Integer getCapacidadeMaxima() { return capacidadeMaxima; }
    public void setCapacidadeMaxima(Integer capacidadeMaxima) { this.capacidadeMaxima = capacidadeMaxima; }

    public Usuario getCriador() { return criador; }
    public void setCriador(Usuario criador) { this.criador = criador; }

    public List<Usuario> getParticipantes() { return participantes; }
    public void setParticipantes(List<Usuario> participantes) { this.participantes = participantes; }

    public Postagem getPostagem() { return postagem; }
    public void setPostagem(Postagem postagem) { this.postagem = postagem; }
    
    // Métodos auxiliares
    
    /**
     * Adiciona um participante ao evento
     */
    public void adicionarParticipante(Usuario usuario) {
        if (!this.participantes.contains(usuario)) {
            this.participantes.add(usuario);
        }
    }
    
    /**
     * Remove um participante do evento
     */
    public void removerParticipante(Usuario usuario) {
        this.participantes.remove(usuario);
    }
    
    /**
     * Verifica se o evento está lotado
     */
    public boolean isLotado() {
        return capacidadeMaxima != null && participantes.size() >= capacidadeMaxima;
    }
    
    /**
     * Retorna o número de participantes confirmados
     */
    public int getTotalParticipantes() {
        return participantes.size();
    }
    
    /**
     * Retorna o número de vagas disponíveis
     */
    public Integer getVagasDisponiveis() {
        if (capacidadeMaxima == null) {
            return null; // Sem limite de vagas
        }
        return Math.max(0, capacidadeMaxima - participantes.size());
    }
    
    /**
     * Método auxiliar para acessar as imagens do evento através do post
     */
    public List<Imagem> getImagens() {
        return postagem != null ? postagem.getImagens() : new ArrayList<>();
    }
    
    /**
     * Retorna data e hora de início combinados
     */
    public LocalDateTime getDataHoraInicio() {
        if (dataEvento != null && horarioInicio != null) {
            return LocalDateTime.of(dataEvento, horarioInicio);
        }
        return null;
    }
    
    /**
     * Retorna data e hora de fim combinados
     */
    public LocalDateTime getDataHoraFim() {
        if (dataEvento != null && horarioFim != null) {
            return LocalDateTime.of(dataEvento, horarioFim);
        }
        return null;
    }
}
