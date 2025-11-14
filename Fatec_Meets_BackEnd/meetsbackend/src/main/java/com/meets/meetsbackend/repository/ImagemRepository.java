package com.meets.meetsbackend.repository;

import com.meets.meetsbackend.model.Imagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImagemRepository extends JpaRepository<Imagem, Long> {
    
    // Buscar todas as imagens de uma postagem específica
    List<Imagem> findByPostagemIdOrderByOrdemAsc(Long postagemId);
    
    // Contar quantas imagens uma postagem tem
    Long countByPostagemId(Long postagemId);
}
