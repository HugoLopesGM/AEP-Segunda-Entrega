package com.AEP.AEP.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class SolicitacaoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long protocolo;

    private String descricao;
    private String prioridade;
    private String rua;

    // Campos do Wireframe
    private String categoria;
    private LocalDate previsaoConclusao;


    @Enumerated(EnumType.STRING)
    private Status statusAtual;
    private String autor;

    private boolean anonimo;

    @ManyToOne
    private CidadaoModel solicitante;

    @OneToMany(mappedBy = "solicitacao", cascade = CascadeType.ALL)
    private List<HistoricoStatusModel> historico = new ArrayList<>();
}