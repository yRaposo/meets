package com.meets.meetsbackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO para criacao de Postagem.
 */
public class PostagemRequest {

    @NotBlank(message = "Titulo e obrigatorio")
    @Size(min = 5, max = 200, message = "Titulo deve ter entre 5 e 200 caracteres")
    private String titulo;

    @NotBlank(message = "Conteudo e obrigatorio")
    @Size(min = 10, message = "Conteudo deve ter no minimo 10 caracteres")
    private String conteudo;

    @NotNull(message = "ID do usuario e obrigatorio")
    private Long usuarioId;

    // Construtores
    public PostagemRequest() {}

    public PostagemRequest(String titulo, String conteudo, Long usuarioId) {
        this.titulo = titulo;
        this.conteudo = conteudo;
        this.usuarioId = usuarioId;
    }

    // Getters e Setters
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    
    public String getConteudo() { return conteudo; }
    public void setConteudo(String conteudo) { this.conteudo = conteudo; }
    
    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
}
