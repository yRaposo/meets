package com.meets.meetsbackend.service;

import com.meets.meetsbackend.model.Instituicao;
import com.meets.meetsbackend.repository.InstituicaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class InstituicaoService {

    private final InstituicaoRepository instituicaoRepository;

    public InstituicaoService(InstituicaoRepository instituicaoRepository) {
        this.instituicaoRepository = instituicaoRepository;
    }

    public List<Instituicao> listarTodos() { return instituicaoRepository.findAll(); }
    public Optional<Instituicao> buscarPorId(Long id) { return instituicaoRepository.findById(id); }
    public Instituicao criar(Instituicao i) { return instituicaoRepository.save(i); }
    public Optional<Instituicao> atualizar(Long id, Instituicao dados) {
        return instituicaoRepository.findById(id).map(inst -> {
            inst.setNome(dados.getNome());
            return instituicaoRepository.save(inst);
        });
    }
    public void excluir(Long id) { instituicaoRepository.deleteById(id); }
}
