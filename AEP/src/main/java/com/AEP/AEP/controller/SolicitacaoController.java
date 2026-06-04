package com.AEP.AEP.controller;

import com.AEP.AEP.model.SolicitacaoModel;
import com.AEP.AEP.model.Status;
import com.AEP.AEP.service.SolicitacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        boolean isAdmin = auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (isAdmin) {
            model.addAttribute("listaSolicitacoes", service.listar());
            model.addAttribute("qtdAbertas", service.contarAbertas());
            model.addAttribute("qtdEmAndamento", service.contarEmAndamento());
            model.addAttribute("qtdResolvidas", service.contarResolvidas());
        } else {
            model.addAttribute("listaSolicitacoes", service.listarPorAutor(username));
            model.addAttribute("qtdAbertas", service.contarAbertasPorAutor(username));
            model.addAttribute("qtdEmAndamento", service.contarEmAndamentoPorAutor(username));
            model.addAttribute("qtdResolvidas", service.contarResolvidasPorAutor(username));
        }

        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("nomeUsuario", username);
        return "index";
    }

    @GetMapping("/nova")
    public String novaSolicitacao(Model model) {
        model.addAttribute("solicitacao", new SolicitacaoModel());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("nomeUsuario", auth.getName());
        model.addAttribute("isAdmin", auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));

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

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            model.addAttribute("nomeUsuario", auth.getName());
            model.addAttribute("isAdmin", auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));

            return "form";
        }
    }

    @GetMapping("/detalhes/{id}")
    public String verDetalhes(@PathVariable Long id, Model model) {
        SolicitacaoModel solicitacao = service.buscarPorId(id).orElseThrow();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        boolean isAdmin = auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        model.addAttribute("solicitacao", solicitacao);
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("nomeUsuario", username);

        return "detalhes";
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id) {
        service.deletar(id);
        return "redirect:/solicitacoes";
    }

    @PostMapping("/atualizar-status/{id}")
    public String atualizarStatus(@PathVariable Long id, @RequestParam Status status) {
        service.atualizarStatus(id, status);
        return "redirect:/solicitacoes";
    }

    @PostMapping("/adicionar-comentario/{id}")
    public String adicionarComentario(@PathVariable Long id, @RequestParam String comentario) {
        service.adicionarComentarioAdmin(id, comentario);
        return "redirect:/solicitacoes/detalhes/" + id;
    }
}