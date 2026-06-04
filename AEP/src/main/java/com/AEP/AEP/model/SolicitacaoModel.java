package com.AEP.AEP.model;

import jakarta.persistence.*;
import lombok.Data;

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

    @ManyToOne
    private CidadaoModel solicitante;

    @OneToMany(mappedBy = "solicitacao", cascade = CascadeType.ALL)
    private List<HistoricoStatusModel> historico = new ArrayList<>();
}
