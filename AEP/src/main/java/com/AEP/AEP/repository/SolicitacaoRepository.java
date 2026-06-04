package com.AEP.AEP.repository;

import com.AEP.AEP.model.SolicitacaoModel;
import com.AEP.AEP.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SolicitacaoRepository extends JpaRepository<SolicitacaoModel, Long> {

    long countByStatusAtual(Status statusAtual);


    List<SolicitacaoModel> findByAutor(String autor);
    long countByStatusAtualAndAutor(Status statusAtual, String autor);
}