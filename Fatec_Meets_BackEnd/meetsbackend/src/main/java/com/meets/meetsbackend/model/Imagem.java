package com.meets.meetsbackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

/**
 * Entidade Imagem
 * Representa uma imagem associada a uma postagem.
 * Múltiplas imagens podem ser associadas a um post.
 */
@Entity
@Table(name = "imagem")
public class Imagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500)
    private String url;

    @Column(length = 200)
    private String descricao;  // Descrição/alt text da imagem

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postagem_id", nullable = false)
    @com.fasterxml.jackson.annotation.JsonBackReference
    private Postagem postagem;

    @Column(nullable = false)
    private Integer ordem = 0;  // Ordem de exibição das imagens

    public Imagem() {}

    public Imagem(String url, Postagem postagem) {
        this.url = url;
        this.postagem = postagem;
    }

    public Imagem(String url, String descricao, Postagem postagem, Integer ordem) {
        this.url = url;
        this.descricao = descricao;
        this.postagem = postagem;
        this.ordem = ordem;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    
    public Postagem getPostagem() { return postagem; }
    public void setPostagem(Postagem postagem) { this.postagem = postagem; }
    
    public Integer getOrdem() { return ordem; }
    public void setOrdem(Integer ordem) { this.ordem = ordem; }
}
