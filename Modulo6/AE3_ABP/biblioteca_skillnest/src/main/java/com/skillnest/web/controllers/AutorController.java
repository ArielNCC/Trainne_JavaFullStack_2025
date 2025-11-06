package com.skillnest.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.skillnest.web.models.Autor;
import com.skillnest.web.services.AutorService;

/**
 * Controlador para gestionar Autores
 * Usa el servicio AutorService para operaciones JPA
 */
@Controller
@RequestMapping("/autores")
public class AutorController {

    @Autowired
    private AutorService autorService;

    /**
     * Listar todos los autores
     */
    @GetMapping
    public String listarAutores(Model model) {
        List<Autor> autores = autorService.listarTodos();
        model.addAttribute("autores", autores);
        return "autores/lista-autores";
    }

    /**
     * Mostrar formulario para crear autor
     */
    @GetMapping("/crear")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("autor", new Autor());
        return "autores/crear";
    }

    /**
     * Guardar un nuevo autor
     * Validación: nombre y apellido son obligatorios
     */
    @PostMapping("/guardar")
    public String guardarAutor(
            @RequestParam String nombre,
            @RequestParam String apellido,
            @RequestParam(required = false) String nacionalidad,
            @RequestParam(required = false) String fechaNacimiento,
            @RequestParam(required = false) String biografia,
            Model model) {
        
        try {
            // Validación: nombre y apellido no pueden estar vacíos
            if (nombre == null || nombre.trim().isEmpty()) {
                model.addAttribute("error", "El nombre del autor es obligatorio");
                return "autores/crear";
            }
            if (apellido == null || apellido.trim().isEmpty()) {
                model.addAttribute("error", "El apellido del autor es obligatorio");
                return "autores/crear";
            }
            
            Autor autor = new Autor();
            autor.setNombre(nombre.trim());
            autor.setApellido(apellido.trim());
            autor.setNacionalidad(nacionalidad);
            
            // Convertir fecha de nacimiento si existe
            if (fechaNacimiento != null && !fechaNacimiento.isEmpty()) {
                autor.setFechaNacimiento(java.time.LocalDate.parse(fechaNacimiento));
            }
            
            autor.setBiografia(biografia);

            // Operación transaccional - si falla, se revierte automáticamente
            autorService.registrarAutor(autor);
            
            model.addAttribute("mensaje", "Autor registrado exitosamente");
            model.addAttribute("autor", autor);
            return "autores/detalle";
            
        } catch (Exception e) {
            model.addAttribute("error", "Error al guardar el autor: " + e.getMessage());
            return "autores/crear";
        }
    }

    /**
     * Ver detalles de un autor
     */
    @GetMapping("/detalle/{id}")
    public String verDetalle(@PathVariable Long id, Model model) {
        try {
            Autor autor = autorService.buscarPorId(id);
            if (autor == null) {
                model.addAttribute("error", "Autor no encontrado");
                return "autores/lista-autores";
            }
            model.addAttribute("autor", autor);
            return "autores/detalle";
            
        } catch (Exception e) {
            model.addAttribute("error", "Error al buscar el autor: " + e.getMessage());
            return "autores/lista-autores";
        }
    }

    /**
     * Mostrar formulario para editar autor
     */
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        try {
            Autor autor = autorService.buscarPorId(id);
            if (autor == null) {
                model.addAttribute("error", "Autor no encontrado");
                return "redirect:/autores";
            }
            model.addAttribute("autor", autor);
            return "autores/editar";
            
        } catch (Exception e) {
            model.addAttribute("error", "Error al cargar el autor: " + e.getMessage());
            return "redirect:/autores";
        }
    }

    /**
     * Actualizar un autor
     * Validación: nombre y apellido son obligatorios
     */
    @PostMapping("/actualizar")
    public String actualizarAutor(
            @RequestParam Long id,
            @RequestParam String nombre,
            @RequestParam String apellido,
            @RequestParam(required = false) String nacionalidad,
            @RequestParam(required = false) String fechaNacimiento,
            @RequestParam(required = false) String biografia,
            Model model) {
        
        try {
            // Validación: nombre y apellido no pueden estar vacíos
            if (nombre == null || nombre.trim().isEmpty()) {
                model.addAttribute("error", "El nombre del autor es obligatorio");
                Autor autor = autorService.buscarPorId(id);
                model.addAttribute("autor", autor);
                return "autores/editar";
            }
            if (apellido == null || apellido.trim().isEmpty()) {
                model.addAttribute("error", "El apellido del autor es obligatorio");
                Autor autor = autorService.buscarPorId(id);
                model.addAttribute("autor", autor);
                return "autores/editar";
            }
            
            Autor autor = autorService.buscarPorId(id);
            if (autor == null) {
                model.addAttribute("error", "Autor no encontrado");
                return "redirect:/autores";
            }

            autor.setNombre(nombre.trim());
            autor.setApellido(apellido.trim());
            autor.setNacionalidad(nacionalidad);
            
            // Convertir fecha de nacimiento si existe
            if (fechaNacimiento != null && !fechaNacimiento.isEmpty()) {
                autor.setFechaNacimiento(java.time.LocalDate.parse(fechaNacimiento));
            }
            
            autor.setBiografia(biografia);

            // Operación transaccional - si falla, se revierte automáticamente
            autorService.actualizarAutor(autor);
            
            model.addAttribute("mensaje", "Autor actualizado exitosamente");
            model.addAttribute("autor", autor);
            return "autores/detalle";
            
        } catch (Exception e) {
            model.addAttribute("error", "Error al actualizar el autor: " + e.getMessage());
            return "autores/editar";
        }
    }

    /**
     * Eliminar un autor
     */
    @GetMapping("/eliminar/{id}")
    public String eliminarAutor(@PathVariable Long id, Model model) {
        try {
            autorService.eliminarAutor(id);
            model.addAttribute("mensaje", "Autor eliminado exitosamente");
            return "redirect:/autores";
            
        } catch (Exception e) {
            model.addAttribute("error", "Error al eliminar el autor: " + e.getMessage());
            return "redirect:/autores";
        }
    }

    /**
     * Buscar autores por apellido
     */
    @GetMapping("/buscar")
    public String buscarAutores(@RequestParam String apellido, Model model) {
        try {
            List<Autor> autores = autorService.buscarPorApellido(apellido);
            model.addAttribute("autores", autores);
            model.addAttribute("busqueda", apellido);
            return "autores/lista-autores";
            
        } catch (Exception e) {
            model.addAttribute("error", "Error en la búsqueda: " + e.getMessage());
            return "autores/lista-autores";
        }
    }
}
