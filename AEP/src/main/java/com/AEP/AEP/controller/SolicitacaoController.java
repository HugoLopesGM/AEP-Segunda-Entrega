package com.AEP.AEP.controller;
import com.AEP.AEP.model.SolicitacaoModel;
import com.AEP.AEP.service.SolicitacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/solicitacoes")
public class SolicitacaoController {

    @Autowired
    private SolicitacaoService service;

    @GetMapping
    public String listar(Model model) {

        model.addAttribute(
                "listaSolicitacoes",
                service.listar()
        );

        return "index";
    }

    @GetMapping("/nova")
    public String novaSolicitacao(Model model) {

        model.addAttribute(
                "solicitacao",
                new SolicitacaoModel()
        );

        return "form";
    }

    @PostMapping("/salvar")
    public String salvar(SolicitacaoModel solicitacao) {

        service.salvar(solicitacao);

        return "redirect:/solicitacoes";
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id) {

        service.deletar(id);

        return "redirect:/solicitacoes";
    }
}