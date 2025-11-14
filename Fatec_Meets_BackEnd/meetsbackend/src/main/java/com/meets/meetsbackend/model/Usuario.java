package com.meets.meetsbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidade Usuario
 * Representa um usuário do sistema (aluno, docente, etc.).
 * Observação: Para simplificar, usamos campos básicos. Em produção,
 * separe DTOs para entrada/saída e não exponha senhas diretamente.
 */
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(unique = true, nullable = false, length = 100)
    private String email;

    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)  // Aceita senha no JSON de entrada, mas nunca envia na resposta
    private String senha;

    @Column(length = 500)
    private String bio;

    @Column(length = 500)
    private String fotoPerfil;  // URL da foto de perfil

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore  // Evita loop infinito na serialização JSON
    private List<Postagem> postagens = new ArrayList<>();

    public Usuario() {}

    public Usuario(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public Usuario(String nome, String email, String senha, String bio) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.bio = bio;
    }

    // Getters e setters gerados manualmente para clareza
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
    public String getFotoPerfil() { return fotoPerfil; }
    public void setFotoPerfil(String fotoPerfil) { this.fotoPerfil = fotoPerfil; }
    public List<Postagem> getPostagens() { return postagens; }
    public void setPostagens(List<Postagem> postagens) { this.postagens = postagens; }
}
