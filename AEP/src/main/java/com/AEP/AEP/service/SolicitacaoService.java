package com.AEP.AEP.service;

import com.AEP.AEP.model.HistoricoStatusModel;
import com.AEP.AEP.model.SolicitacaoModel;
import com.AEP.AEP.model.Status;
import com.AEP.AEP.repository.SolicitacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
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


        if (solicitacao.getDescricao() == null || solicitacao.getDescricao().length() < 10) {
            throw new IllegalArgumentException("A descrição deve ter no mínimo 10 caracteres.");
        }


        if (solicitacao.getProtocolo() == null) {
            solicitacao.setStatusAtual(Status.ABERTO);


            solicitacao.setPrevisaoConclusao(LocalDate.now().plusDays(15));

            HistoricoStatusModel historicoInicial = new HistoricoStatusModel();
            historicoInicial.setStatus(Status.ABERTO);
            historicoInicial.setComentario("Denúncia recebida pelo sistema.");
            historicoInicial.setResponsavel("Sistema");
            historicoInicial.setData(LocalDateTime.now());
            historicoInicial.setSolicitacao(solicitacao);

            solicitacao.getHistorico().add(historicoInicial);
        }

        return repository.save(solicitacao);
    }

    public Optional<SolicitacaoModel> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }


    public long contarAbertas() {
        return repository.countByStatusAtual(Status.ABERTO);
    }

    public long contarEmAndamento() {
        return repository.countByStatusAtual(Status.EM_EXECUCAO);
    }

    public long contarResolvidas() {
        return repository.countByStatusAtual(Status.RESOLVIDO);
    }
}