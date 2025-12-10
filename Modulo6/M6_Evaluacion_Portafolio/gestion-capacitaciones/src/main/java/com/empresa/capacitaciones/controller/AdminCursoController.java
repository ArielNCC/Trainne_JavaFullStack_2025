package com.empresa.capacitaciones.controller;

import com.empresa.capacitaciones.entity.Curso;
import com.empresa.capacitaciones.service.CursoService;
import com.empresa.capacitaciones.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/cursos")
public class AdminCursoController {
    
    @Autowired
    private CursoService cursoService;
    
    @Autowired
    private InstructorService instructorService;
    
    @GetMapping
    public String listarCursos(Model model) {
        model.addAttribute("cursos", cursoService.findAll());
        return "admin/cursos-listar";
    }
    
    @GetMapping("/crear")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("curso", new Curso());
        model.addAttribute("instructores", instructorService.findAll());
        return "admin/cursos-crear";
    }
    
    @PostMapping("/guardar")
    public String guardarCurso(@ModelAttribute("curso") Curso curso,
                               RedirectAttributes redirectAttributes) {
        try {
            cursoService.save(curso);
            redirectAttributes.addFlashAttribute("mensaje", "Curso creado exitosamente");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Error al crear el curso: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensaje", "danger");
        }
        return "redirect:/admin/cursos";
    }
    
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Curso curso = cursoService.findById(id)
                .orElseThrow(() -> new Exception("Curso no encontrado"));
            model.addAttribute("curso", curso);
            model.addAttribute("instructores", instructorService.findAll());
            return "admin/cursos-editar";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensaje", "danger");
            return "redirect:/admin/cursos";
        }
    }
    
    @PostMapping("/actualizar")
    public String actualizarCurso(@ModelAttribute("curso") Curso curso,
                                  RedirectAttributes redirectAttributes) {
        try {
            cursoService.save(curso);
            redirectAttributes.addFlashAttribute("mensaje", "Curso actualizado exitosamente");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Error al actualizar el curso: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensaje", "danger");
        }
        return "redirect:/admin/cursos";
    }
    
    @GetMapping("/eliminar/{id}")
    public String eliminarCurso(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            cursoService.deleteById(id);
            redirectAttributes.addFlashAttribute("mensaje", "Curso eliminado exitosamente");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Error al eliminar el curso: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensaje", "danger");
        }
        return "redirect:/admin/cursos";
    }
    
    @GetMapping("/detalle/{id}")
    public String verDetalleCurso(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Curso curso = cursoService.findById(id)
                .orElseThrow(() -> new Exception("Curso no encontrado"));
            model.addAttribute("curso", curso);
            return "admin/cursos-detalle";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensaje", "danger");
            return "redirect:/admin/cursos";
        }
    }
}
