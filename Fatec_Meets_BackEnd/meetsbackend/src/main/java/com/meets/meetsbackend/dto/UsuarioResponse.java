package com.meets.meetsbackend.dto;

/**
 * DTO para resposta de Usuario.
 * Usado nas respostas GET/POST (sem expor senha).
 */
public class UsuarioResponse {

    private Long id;
    private String nome;
    private String email;

    // Construtores
    public UsuarioResponse() {}

    public UsuarioResponse(Long id, String nome, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
