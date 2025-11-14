package com.meets.meetsbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidade Postagem
 * Representa uma publicação/post feita por um usuario.
 * Um post pode ter um evento associado (opcional).
 */
@Entity
@Table(name = "postagem")
public class Postagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String titulo;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String conteudo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonIgnoreProperties({"postagens", "senha"})  // Ignora campos que causariam loop
    private Usuario usuario;

    @Column(nullable = false)
    private LocalDateTime dataCriacao = LocalDateTime.now();

    @Column(length = 500)
    private String imagemUrl;  // URL da imagem do post (DEPRECATED - usar lista de imagens)

    @OneToMany(mappedBy = "postagem", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"postagem"})
    @OrderBy("ordem ASC")
    private List<Imagem> imagens = new ArrayList<>();  // Lista de imagens do post

    @OneToMany(mappedBy = "postagem", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"postagem"})  // Evita loop infinito com comentários
    private List<Comentario> comentarios = new ArrayList<>();

    /**
     * Usuários que deram like neste post
     * Relacionamento ManyToMany para rastrear quem curtiu o post
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "postagem_likes",
        joinColumns = @JoinColumn(name = "postagem_id"),
        inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    @JsonIgnoreProperties({"postagens", "senha", "email"})
    private List<Usuario> likes = new ArrayList<>();

    /**
     * Um post pode ter um evento associado (opcional)
     * Se houver um evento, ele estará aqui
     */
    @OneToOne(mappedBy = "postagem", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    private Evento evento;

    public Postagem() {}

    public Postagem(String titulo, String conteudo, Usuario usuario) {
        this.titulo = titulo;
        this.conteudo = conteudo;
        this.usuario = usuario;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getConteudo() { return conteudo; }
    public void setConteudo(String conteudo) { this.conteudo = conteudo; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }
    public String getImagemUrl() { return imagemUrl; }
    public void setImagemUrl(String imagemUrl) { this.imagemUrl = imagemUrl; }
    public List<Imagem> getImagens() { return imagens; }
    public void setImagens(List<Imagem> imagens) { this.imagens = imagens; }
    public List<Comentario> getComentarios() { return comentarios; }
    public void setComentarios(List<Comentario> comentarios) { this.comentarios = comentarios; }
    public List<Usuario> getLikes() { return likes; }
    public void setLikes(List<Usuario> likes) { this.likes = likes; }
    public Evento getEvento() { return evento; }
    public void setEvento(Evento evento) { this.evento = evento; }

    // Métodos auxiliares para gerenciar imagens
    public void adicionarImagem(Imagem imagem) {
        imagens.add(imagem);
        imagem.setPostagem(this);
    }

    public void removerImagem(Imagem imagem) {
        imagens.remove(imagem);
        imagem.setPostagem(null);
    }

    // Métodos auxiliares para gerenciar likes
    public void adicionarLike(Usuario usuario) {
        if (!likes.contains(usuario)) {
            likes.add(usuario);
        }
    }

    public void removerLike(Usuario usuario) {
        likes.remove(usuario);
    }

    public boolean usuarioCurtiu(Usuario usuario) {
        return likes.contains(usuario);
    }

    public int getTotalLikes() {
        return likes.size();
    }
}
