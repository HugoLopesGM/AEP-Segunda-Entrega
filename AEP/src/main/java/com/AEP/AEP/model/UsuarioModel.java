package com.AEP.AEP.model;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
public class UsuarioModel {
    private String nome;
    private String cpf;
    private String email;
}
