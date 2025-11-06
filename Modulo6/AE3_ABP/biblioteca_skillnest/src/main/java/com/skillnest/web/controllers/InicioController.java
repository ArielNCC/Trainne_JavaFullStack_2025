package com.skillnest.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.skillnest.web.services.AutorService;
import com.skillnest.web.services.LibroService;

/**
 * Controlador principal de Biblioteca-skillnest
 * Muestra la página de inicio con estadísticas
 */
@Controller
public class InicioController {

    @Autowired
    private LibroService libroService;

    @Autowired
    private AutorService autorService;

    @GetMapping("/")
    public String mostrarInicio(Model model) {
        try {
            // Obtener estadísticas para mostrar en la página de inicio
            int totalLibros = libroService.contarLibros();
            int totalAutores = autorService.contarAutores();
            
            model.addAttribute("totalLibros", totalLibros);
            model.addAttribute("totalAutores", totalAutores);
            
        } catch (Exception e) {
            model.addAttribute("error", "Error al cargar estadísticas");
        }
        
        return "inicio"; // Renderiza WEB-INF/views/inicio.jsp
    }
}
