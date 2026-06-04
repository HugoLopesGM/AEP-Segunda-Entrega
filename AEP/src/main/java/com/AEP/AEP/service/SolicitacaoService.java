package com.AEP.AEP.service;

import com.AEP.AEP.model.SolicitacaoModel;
import com.AEP.AEP.repository.SolicitacaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SolicitacaoService {
    private final SolicitacaoRepository repository;

    public SolicitacaoService(SolicitacaoRepository repository) {
        this.repository = repository;
    }

    public SolicitacaoModel salvar(SolicitacaoModel solicitacao) {
        return repository.save(solicitacao);
    }

    public List<SolicitacaoModel> listar() {
        return repository.findAll();
    }

    public SolicitacaoModel buscar(Long id) {
        return repository.findById(id).orElse(null);
    }
}
