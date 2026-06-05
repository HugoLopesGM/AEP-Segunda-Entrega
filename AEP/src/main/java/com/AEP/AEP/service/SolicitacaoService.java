package com.AEP.AEP.service;

import com.AEP.AEP.model.HistoricoStatusModel;
import com.AEP.AEP.model.SolicitacaoModel;
import com.AEP.AEP.model.Status;
import com.AEP.AEP.repository.SolicitacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
            String usuarioLogado = SecurityContextHolder.getContext().getAuthentication().getName();
            solicitacao.setAutor(usuarioLogado);
            solicitacao.setStatusAtual(Status.ABERTO);
            solicitacao.setPrevisaoConclusao(LocalDate.now().plusDays(15));

            HistoricoStatusModel historicoInicial = new HistoricoStatusModel();
            historicoInicial.setStatus(Status.ABERTO);
            historicoInicial.setComentario("Denúncia recebida pelo sistema.");

            String nomeExibicao = solicitacao.isAnonimo() ? "Usuário Anônimo" : usuarioLogado;
            historicoInicial.setResponsavel(nomeExibicao);

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

    public long contarAbertas() { return repository.countByStatusAtual(Status.ABERTO); }
    public long contarEmAndamento() { return repository.countByStatusAtual(Status.EM_EXECUCAO); }
    public long contarResolvidas() { return repository.countByStatusAtual(Status.RESOLVIDO); }

    public List<SolicitacaoModel> listarPorAutor(String autor) { return repository.findByAutor(autor); }
    public long contarAbertasPorAutor(String autor) { return repository.countByStatusAtualAndAutor(Status.ABERTO, autor); }
    public long contarEmAndamentoPorAutor(String autor) { return repository.countByStatusAtualAndAutor(Status.EM_EXECUCAO, autor); }
    public long contarResolvidasPorAutor(String autor) { return repository.countByStatusAtualAndAutor(Status.RESOLVIDO, autor); }

    public void atualizarStatus(Long id, Status novoStatus) {
        SolicitacaoModel solicitacao = repository.findById(id).orElseThrow();
        solicitacao.setStatusAtual(novoStatus);

        HistoricoStatusModel novoHistorico = new HistoricoStatusModel();
        novoHistorico.setStatus(novoStatus);
        novoHistorico.setComentario("Status atualizado pelo Administrador.");
        novoHistorico.setResponsavel("Admin");
        novoHistorico.setData(LocalDateTime.now());
        novoHistorico.setSolicitacao(solicitacao);

        solicitacao.getHistorico().add(novoHistorico);
        repository.save(solicitacao);
    }

    public void adicionarComentarioAdmin(Long id, String comentario) {
        SolicitacaoModel solicitacao = repository.findById(id).orElseThrow();

        HistoricoStatusModel novoHistorico = new HistoricoStatusModel();
        novoHistorico.setStatus(solicitacao.getStatusAtual());
        novoHistorico.setComentario(comentario);
        novoHistorico.setResponsavel("Admin");
        novoHistorico.setData(LocalDateTime.now());
        novoHistorico.setSolicitacao(solicitacao);

        solicitacao.getHistorico().add(novoHistorico);
        repository.save(solicitacao);
    }
}