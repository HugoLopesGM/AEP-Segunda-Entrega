package com.AEP.AEP.service;

import com.AEP.AEP.model.SolicitacaoModel;
import com.AEP.AEP.repository.SolicitacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SolicitacaoService {
    @Autowired
    private SolicitacaoRepository repository;

    public List<SolicitacaoModel> listar() {
        return repository.findAll();
    }


    public SolicitacaoModel salvar(SolicitacaoModel solicitacao) {
        return repository.save(solicitacao);
    }


    public Optional<SolicitacaoModel> buscarPorId(Long id) {
        return repository.findById(id);
    }


    public void deletar(Long id) {
        repository.deleteById(id);
    }


    public SolicitacaoModel alterarSolicitacao(
            Long id,
            SolicitacaoModel solicitacao) {

        solicitacao.setProtocolo(id);

        return solicitacao;
    }
}