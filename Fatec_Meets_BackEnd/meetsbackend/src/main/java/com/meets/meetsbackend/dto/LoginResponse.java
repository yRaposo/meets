package com.meets.meetsbackend.dto;

/**
 * DTO para resposta de login com token JWT.
 */
public class LoginResponse {

    private String token;
    private String tipo = "Bearer";
    private UsuarioResponse usuario;

    // Construtores
    public LoginResponse() {}

    public LoginResponse(String token, UsuarioResponse usuario) {
        this.token = token;
        this.usuario = usuario;
    }

    // Getters e Setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    
    public UsuarioResponse getUsuario() { return usuario; }
    public void setUsuario(UsuarioResponse usuario) { this.usuario = usuario; }
}
