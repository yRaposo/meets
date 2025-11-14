package com.meets.meetsbackend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tipo_telefone")
public class TipoTelefone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String descricao;

    public TipoTelefone() {}
    public TipoTelefone(String descricao) { this.descricao = descricao; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
}
