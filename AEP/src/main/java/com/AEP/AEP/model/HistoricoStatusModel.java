package com.AEP.AEP.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class HistoricoStatusModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String comentario;

    private String responsavel;

    private LocalDateTime data;

    @ManyToOne
    private SolicitacaoModel solicitacao;
}