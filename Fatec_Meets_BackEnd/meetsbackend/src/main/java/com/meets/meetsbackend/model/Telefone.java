package com.meets.meetsbackend.model;

import jakarta.persistence.*;

/**
 * Telefone simples associado a usuario ou instituicao
 */
@Entity
@Table(name = "telefone")
public class Telefone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20)
    private String numero;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_telefone_id")
    private TipoTelefone tipo;

    public Telefone() {}
    public Telefone(String numero, TipoTelefone tipo) { this.numero = numero; this.tipo = tipo; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }
    public TipoTelefone getTipo() { return tipo; }
    public void setTipo(TipoTelefone tipo) { this.tipo = tipo; }
}
