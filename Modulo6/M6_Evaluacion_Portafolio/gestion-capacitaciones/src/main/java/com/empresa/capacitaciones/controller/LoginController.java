package com.empresa.capacitaciones.controller;

import com.empresa.capacitaciones.security.UserEntity;
import com.empresa.capacitaciones.security.UserRepository;
import com.empresa.capacitaciones.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmpleadoService empleadoService;
    
    @GetMapping("/")
    public String home() {
        return "redirect:/login";
    }
    
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/panel")
    public String panel(Authentication authentication) {
        if (authentication != null) {
            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
                return "redirect:/admin/cursos";
            } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_EMPLEADO"))) {
                return "redirect:/empleado/cursos";
            }
        }
        return "redirect:/login";
    }

    @GetMapping("/perfil")
    public String perfil(Model model, Authentication authentication) {
        if (authentication == null) {
            return "redirect:/login";
        }
        
        String username = authentication.getName();
        UserEntity user = userRepository.findByUsername(username).orElse(null);
        
        if (user != null) {
            model.addAttribute("usuario", user);
            
            // Buscar si es empleado por email
            empleadoService.findByEmail(user.getEmail()).ifPresent(empleado -> {
                model.addAttribute("empleado", empleado);
            });
        }
        
        return "perfil";
    }
}
