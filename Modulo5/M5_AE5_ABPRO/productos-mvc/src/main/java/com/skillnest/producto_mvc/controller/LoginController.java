package com.skillnest.producto_mvc.controller;

import com.skillnest.producto_mvc.model.Usuario;
import com.skillnest.producto_mvc.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    
    @Autowired
    private UsuarioService usuarioService;
    
    @GetMapping("/")
    public String index() {
        return "redirect:/login";
    }
    
    @GetMapping("/login")
    public String mostrarLogin(Model model, HttpSession session) {
        // Si ya hay sesión activa, redirigir al dashboard
        if (session.getAttribute("usuario") != null) {
            return "redirect:/dashboard";
        }
        return "login";
    }
    
    @PostMapping("/login")
    public String procesarLogin(
            @RequestParam String username,
            @RequestParam String password,
            HttpSession session,
            Model model) {
        
        Usuario usuario = usuarioService.autenticar(username, password);
        
        if (usuario != null) {
            session.setAttribute("usuario", usuario);
            session.setAttribute("nombreUsuario", usuario.getNombreCompleto());
            return "redirect:/dashboard";
        } else {
            model.addAttribute("error", "Usuario o contraseña incorrectos");
            return "login";
        }
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
    
    @GetMapping("/dashboard")
    public String mostrarDashboard(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        
        if (usuario == null) {
            return "redirect:/login";
        }
        
        model.addAttribute("nombreUsuario", usuario.getNombreCompleto());
        return "dashboard";
    }
}
