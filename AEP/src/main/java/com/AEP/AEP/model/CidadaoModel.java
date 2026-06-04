package com.AEP.AEP.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class CidadaoModel extends UsuarioModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean anonimo;

    public String getIdentidadeParaExibicao() {
        if (anonimo) {
            return "USUÁRIO ANÔNIMO";
        }

        return getNome();
    }
}
