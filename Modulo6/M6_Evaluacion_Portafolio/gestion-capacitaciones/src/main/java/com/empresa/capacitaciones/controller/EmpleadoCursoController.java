package com.empresa.capacitaciones.controller;

import com.empresa.capacitaciones.entity.Curso;
import com.empresa.capacitaciones.entity.Empleado;
import com.empresa.capacitaciones.security.UserEntity;
import com.empresa.capacitaciones.security.UserRepository;
import com.empresa.capacitaciones.service.CursoService;
import com.empresa.capacitaciones.service.EmpleadoService;
import com.empresa.capacitaciones.service.InscripcionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/empleado/cursos")
public class EmpleadoCursoController {
    
    @Autowired
    private CursoService cursoService;
    
    @Autowired
    private InscripcionService inscripcionService;
    
    @Autowired
    private EmpleadoService empleadoService;

    @Autowired
    private UserRepository userRepository;
    
    @GetMapping
    public String listarCursosDisponibles(Model model, Authentication authentication) {
        model.addAttribute("cursos", cursoService.findCursosConCuposDisponibles());
        
        // Obtener empleado actual si existe
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            userRepository.findByUsername(username).ifPresent(user -> {
                empleadoService.findByEmail(user.getEmail()).ifPresent(empleado -> {
                    model.addAttribute("empleado", empleado);
                    model.addAttribute("misInscripciones", inscripcionService.findByEmpleadoId(empleado.getId()));
                });
            });
        }
        
        return "empleado/cursos-disponibles";
    }
    
    /* Movido a EmpleadoController
    @GetMapping("/mis-cursos")
    public String listarMisCursos(Model model, Authentication authentication, RedirectAttributes redirectAttributes) {
        ...
    }
    */
    
    @PostMapping("/inscribir/{cursoId}")
    public String inscribirseEnCurso(@PathVariable Long cursoId, 
                                     Authentication authentication,
                                     RedirectAttributes redirectAttributes) {
        try {
            if (authentication == null || !authentication.isAuthenticated()) {
                redirectAttributes.addFlashAttribute("mensaje", "Debe iniciar sesión");
                redirectAttributes.addFlashAttribute("tipoMensaje", "warning");
                return "redirect:/login";
            }
            
            String username = authentication.getName();
            UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new Exception("Usuario no encontrado"));

            Empleado empleado = empleadoService.findByEmail(user.getEmail())
                .orElseThrow(() -> new Exception("Empleado no encontrado"));
            
            inscripcionService.inscribirEmpleado(empleado.getId(), cursoId);
            redirectAttributes.addFlashAttribute("mensaje", "Inscripción realizada exitosamente");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Error: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensaje", "danger");
        }
        return "redirect:/empleado/cursos";
    }
    
    @GetMapping("/cancelar/{inscripcionId}")
    public String cancelarInscripcion(@PathVariable Long inscripcionId, 
                                      RedirectAttributes redirectAttributes) {
        try {
            inscripcionService.cancelarInscripcion(inscripcionId);
            redirectAttributes.addFlashAttribute("mensaje", "Inscripción cancelada exitosamente");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Error: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensaje", "danger");
        }
        return "redirect:/empleado/cursos/mis-cursos";
    }
    
    @GetMapping("/detalle/{id}")
    public String verDetalleCurso(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Curso curso = cursoService.findById(id)
                .orElseThrow(() -> new Exception("Curso no encontrado"));
            model.addAttribute("curso", curso);
            return "empleado/curso-detalle";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensaje", "danger");
            return "redirect:/empleado/cursos";
        }
    }
}
