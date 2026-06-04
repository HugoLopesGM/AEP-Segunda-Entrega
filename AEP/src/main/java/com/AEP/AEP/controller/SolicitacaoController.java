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


        model.addAttribute("listaSolicitacoes", service.listar());

        model.addAttribute("qtdAbertas", service.contarAbertas());
        model.addAttribute("qtdEmAndamento", service.contarEmAndamento());
        model.addAttribute("qtdResolvidas", service.contarResolvidas());

        return "index";
    }

    @GetMapping("/nova")
    public String novaSolicitacao(Model model) {
        model.addAttribute("solicitacao", new SolicitacaoModel());
        return "form";
    }

    @PostMapping("/salvar")
    public String salvar(SolicitacaoModel solicitacao, Model model) {
        try {
            service.salvar(solicitacao);
            return "redirect:/solicitacoes";
        } catch (IllegalArgumentException e) {
            model.addAttribute("erro", e.getMessage());
            model.addAttribute("solicitacao", solicitacao);
            return "form";
        }
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id) {
        service.deletar(id);
        return "redirect:/solicitacoes";
    }
}