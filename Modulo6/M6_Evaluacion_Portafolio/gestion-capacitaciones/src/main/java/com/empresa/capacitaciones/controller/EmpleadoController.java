package com.empresa.capacitaciones.controller;

import com.empresa.capacitaciones.entity.Empleado;
import com.empresa.capacitaciones.security.UserEntity;
import com.empresa.capacitaciones.security.UserRepository;
import com.empresa.capacitaciones.service.EmpleadoService;
import com.empresa.capacitaciones.service.InscripcionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/empleado")
public class EmpleadoController {

    @Autowired
    private EmpleadoService empleadoService;

    @Autowired
    private InscripcionService inscripcionService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/mis-cursos")
    public String listarMisCursos(Model model, Authentication authentication, RedirectAttributes redirectAttributes) {
        if (authentication == null || !authentication.isAuthenticated()) {
            redirectAttributes.addFlashAttribute("mensaje", "Debe iniciar sesión");
            redirectAttributes.addFlashAttribute("tipoMensaje", "warning");
            return "redirect:/login";
        }
        
        String username = authentication.getName();
        UserEntity user = userRepository.findByUsername(username).orElse(null);
        
        if (user == null) {
             redirectAttributes.addFlashAttribute("mensaje", "Usuario no encontrado");
             redirectAttributes.addFlashAttribute("tipoMensaje", "danger");
             return "redirect:/login";
        }

        Empleado empleado = empleadoService.findByEmail(user.getEmail()).orElse(null);
        
        if (empleado == null) {
            redirectAttributes.addFlashAttribute("mensaje", "Empleado no encontrado para el usuario: " + username);
            redirectAttributes.addFlashAttribute("tipoMensaje", "danger");
            return "redirect:/empleado/cursos";
        }
        
        model.addAttribute("empleado", empleado);
        model.addAttribute("inscripciones", inscripcionService.findByEmpleadoId(empleado.getId()));
        return "empleado/mis-cursos";
    }
}
