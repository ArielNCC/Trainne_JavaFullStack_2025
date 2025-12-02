package com.skillnest.cliente_rest.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.skillnest.cliente_rest.model.Usuario;
import com.skillnest.cliente_rest.security.JwtUtil;
import com.skillnest.cliente_rest.service.UsuarioService;

@Controller
public class AuthViewController {

    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/panel")
    public String panel(Model model, Principal principal) {
        Usuario usuario = usuarioService.buscarPorNombreUsuario(principal.getName());
        model.addAttribute("usuario", usuario);
        return "panel";
    }

    @GetMapping("/perfil/usuario")
    public String perfil(Model model, Principal principal) {
        Usuario usuario = usuarioService.buscarPorNombreUsuario(principal.getName());
        model.addAttribute("usuario", usuario);
        return "perfil";
    }

    @GetMapping("/admin/panel")
    public String adminPanel(Model model, Principal principal) {
        Usuario usuario = usuarioService.buscarPorNombreUsuario(principal.getName());
        model.addAttribute("usuario", usuario);
        return "admin";
    }
    
    @GetMapping("/api-tester")
    public String apiTester(Model model, Principal principal) {
        Usuario usuario = usuarioService.buscarPorNombreUsuario(principal.getName());
        String token = jwtUtil.generarToken(usuario.getNombreUsuario());
        model.addAttribute("usuario", usuario);
        model.addAttribute("token", token);
        return "api-tester";
    }
}