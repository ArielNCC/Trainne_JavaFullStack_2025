package com.skillnest.agendacontactos.controller;

import com.skillnest.agendacontactos.service.ContactoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controlador principal para manejo de página de inicio
 * 
 * @Controller: Anota la clase como controlador de Spring MVC
 * @Autowired: Inyecta automáticamente la dependencia del servicio
 */
@Controller
public class HomeController {
    
    @Autowired
    private ContactoService contactoService;
    
    /**
     * Página de inicio de la aplicación
     * Muestra estadísticas básicas y últimos contactos
     * 
     * @param model Modelo para pasar datos a la vista
     * @return nombre de la vista a renderizar
     */
    @GetMapping("/")
    public String inicio(Model model) {
        // Obtener todos los contactos para mostrar estadísticas
        var contactos = contactoService.obtenerTodosLosContactos();
        
        // Pasar datos a la vista
        model.addAttribute("totalContactos", contactos.size());
        model.addAttribute("contactos", contactos);
        
        return "inicio";
    }
    
    /**
     * Redirección alternativa para /contactos
     */
    @GetMapping("/contactos")
    public String contactosIndex() {
        return "redirect:/contactos/listar";
    }
}