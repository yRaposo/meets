package com.meets.meetsbackend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Denuncia - quando um usuário reporta uma postagem/conduta.
 */
@Entity
@Table(name = "denuncia")
public class Denuncia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 300)
    private String motivo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postagem_id")
    private Postagem postagem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario denunciante;

    @Column(nullable = false)
    private LocalDateTime dataDenuncia = LocalDateTime.now();

    public Denuncia() {}
    public Denuncia(String motivo, Postagem postagem, Usuario denunciante) {
        this.motivo = motivo;
        this.postagem = postagem;
        this.denunciante = denunciante;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
    public Postagem getPostagem() { return postagem; }
    public void setPostagem(Postagem postagem) { this.postagem = postagem; }
    public Usuario getDenunciante() { return denunciante; }
    public void setDenunciante(Usuario denunciante) { this.denunciante = denunciante; }
    public LocalDateTime getDataDenuncia() { return dataDenuncia; }
    public void setDataDenuncia(LocalDateTime dataDenuncia) { this.dataDenuncia = dataDenuncia; }
}
