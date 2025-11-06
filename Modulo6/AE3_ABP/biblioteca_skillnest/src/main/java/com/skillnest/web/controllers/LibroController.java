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
import com.skillnest.web.models.Libro;
import com.skillnest.web.services.AutorService;
import com.skillnest.web.services.LibroService;

/**
 * Controlador para gestionar Libros
 * Usa el servicio LibroService para operaciones JPA
 */
@Controller
@RequestMapping("/libros")
public class LibroController {

    @Autowired
    private LibroService libroService;

    @Autowired
    private AutorService autorService;

    /**
     * Listar todos los libros
     */
    @GetMapping
    public String listarLibros(Model model) {
        List<Libro> libros = libroService.listarTodosConAutor();
        model.addAttribute("libros", libros);
        return "libros/lista-libros";
    }

    /**
     * Mostrar formulario para crear libro
     */
    @GetMapping("/crear")
    public String mostrarFormularioCrear(Model model) {
        List<Autor> autores = autorService.listarTodos();
        model.addAttribute("autores", autores);
        model.addAttribute("libro", new Libro());
        return "libros/crear";
    }

    /**
     * Guardar un nuevo libro
     * Validación: título e ISBN son obligatorios
     */
    @PostMapping("/guardar")
    public String guardarLibro(
            @RequestParam String titulo,
            @RequestParam String isbn,
            @RequestParam(required = false) Integer anioPublicacion,
            @RequestParam(required = false) String genero,
            @RequestParam(required = false) String editorial,
            @RequestParam(required = false) Integer numeroPaginas,
            @RequestParam(required = false) Integer cantidadTotal,
            @RequestParam Long autorId,
            Model model) {
        
        try {
            // Validación: título no puede estar vacío
            if (titulo == null || titulo.trim().isEmpty()) {
                model.addAttribute("error", "El título del libro es obligatorio");
                List<Autor> autores = autorService.listarTodos();
                model.addAttribute("autores", autores);
                return "libros/crear";
            }
            
            // Validación: ISBN no puede estar vacío
            if (isbn == null || isbn.trim().isEmpty()) {
                model.addAttribute("error", "El ISBN del libro es obligatorio");
                List<Autor> autores = autorService.listarTodos();
                model.addAttribute("autores", autores);
                return "libros/crear";
            }
            
            Autor autor = autorService.buscarPorId(autorId);
            if (autor == null) {
                model.addAttribute("error", "Autor no encontrado");
                List<Autor> autores = autorService.listarTodos();
                model.addAttribute("autores", autores);
                return "libros/crear";
            }

            Libro libro = new Libro();
            libro.setTitulo(titulo.trim());
            libro.setIsbn(isbn.trim());
            libro.setAnioPublicacion(anioPublicacion);
            libro.setGenero(genero);
            libro.setEditorial(editorial);
            libro.setNumeroPaginas(numeroPaginas);
            libro.setCantidadTotal(cantidadTotal != null ? cantidadTotal : 0);
            libro.setCantidadDisponible(cantidadTotal != null ? cantidadTotal : 0);
            libro.setAutor(autor);

            // Operación transaccional - si falla, se revierte automáticamente
            libroService.registrarLibro(libro);
            
            model.addAttribute("mensaje", "Libro registrado exitosamente");
            model.addAttribute("libro", libro);
            return "libros/detalle";
            
        } catch (Exception e) {
            model.addAttribute("error", "Error al guardar el libro: " + e.getMessage());
            List<Autor> autores = autorService.listarTodos();
            model.addAttribute("autores", autores);
            return "libros/crear";
        }
    }

    /**
     * Ver detalles de un libro
     */
    @GetMapping("/detalle/{id}")
    public String verDetalle(@PathVariable Long id, Model model) {
        try {
            Libro libro = libroService.buscarPorIdConAutor(id);
            if (libro == null) {
                model.addAttribute("error", "Libro no encontrado");
                return "redirect:/libros";
            }
            model.addAttribute("libro", libro);
            return "libros/detalle";
            
        } catch (Exception e) {
            model.addAttribute("error", "Error al buscar el libro: " + e.getMessage());
            return "redirect:/libros";
        }
    }

    /**
     * Mostrar formulario para editar libro
     */
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        try {
            Libro libro = libroService.buscarPorIdConAutor(id);
            if (libro == null) {
                model.addAttribute("error", "Libro no encontrado");
                return "redirect:/libros";
            }
            
            List<Autor> autores = autorService.listarTodos();
            model.addAttribute("libro", libro);
            model.addAttribute("autores", autores);
            return "libros/editar";
            
        } catch (Exception e) {
            model.addAttribute("error", "Error al cargar el libro: " + e.getMessage());
            return "redirect:/libros";
        }
    }

    /**
     * Actualizar un libro
     */
    @PostMapping("/actualizar")
    public String actualizarLibro(
            @RequestParam Long id,
            @RequestParam String titulo,
            @RequestParam String isbn,
            @RequestParam(required = false) Integer anioPublicacion,
            @RequestParam(required = false) String genero,
            @RequestParam(required = false) String editorial,
            @RequestParam(required = false) Integer numeroPaginas,
            @RequestParam(required = false) Integer cantidadTotal,
            @RequestParam(required = false) Integer cantidadDisponible,
            @RequestParam Long autorId,
            Model model) {
        
        try {
            Libro libro = libroService.buscarPorId(id);
            if (libro == null) {
                model.addAttribute("error", "Libro no encontrado");
                return "redirect:/libros";
            }

            Autor autor = autorService.buscarPorId(autorId);
            if (autor == null) {
                model.addAttribute("error", "Autor no encontrado");
                return "redirect:/libros";
            }

            libro.setTitulo(titulo);
            libro.setIsbn(isbn);
            libro.setAnioPublicacion(anioPublicacion);
            libro.setGenero(genero);
            libro.setEditorial(editorial);
            libro.setNumeroPaginas(numeroPaginas);
            libro.setCantidadTotal(cantidadTotal);
            libro.setCantidadDisponible(cantidadDisponible);
            libro.setAutor(autor);

            libroService.actualizarLibro(libro);
            
            model.addAttribute("mensaje", "Libro actualizado exitosamente");
            model.addAttribute("libro", libro);
            return "libros/detalle";
            
        } catch (Exception e) {
            model.addAttribute("error", "Error al actualizar el libro: " + e.getMessage());
            return "redirect:/libros/editar/" + id;
        }
    }

    /**
     * Eliminar un libro
     */
    @GetMapping("/eliminar/{id}")
    public String eliminarLibro(@PathVariable Long id, Model model) {
        try {
            libroService.eliminarLibro(id);
            model.addAttribute("mensaje", "Libro eliminado exitosamente");
            return "redirect:/libros";
            
        } catch (Exception e) {
            model.addAttribute("error", "Error al eliminar el libro: " + e.getMessage());
            return "redirect:/libros";
        }
    }

    /**
     * Buscar libros por título
     */
    @GetMapping("/buscar")
    public String buscarLibros(@RequestParam String titulo, Model model) {
        try {
            List<Libro> libros = libroService.buscarPorTitulo(titulo);
            model.addAttribute("libros", libros);
            model.addAttribute("busqueda", titulo);
            return "libros/lista-libros";
            
        } catch (Exception e) {
            model.addAttribute("error", "Error en la búsqueda: " + e.getMessage());
            return "libros/lista-libros";
        }
    }

    /**
     * Listar libros disponibles
     */
    @GetMapping("/disponibles")
    public String listarDisponibles(Model model) {
        List<Libro> libros = libroService.listarDisponibles();
        model.addAttribute("libros", libros);
        model.addAttribute("soloDisponibles", true);
        return "libros/lista-libros";
    }

    /**
     * Prestar un libro
     */
    @PostMapping("/prestar/{id}")
    public String prestarLibro(@PathVariable Long id, Model model) {
        try {
            libroService.prestarLibro(id);
            model.addAttribute("mensaje", "Libro prestado exitosamente");
            return "redirect:/libros/detalle/" + id;
            
        } catch (Exception e) {
            model.addAttribute("error", "Error al prestar el libro: " + e.getMessage());
            return "redirect:/libros/detalle/" + id;
        }
    }

    /**
     * Devolver un libro
     */
    @PostMapping("/devolver/{id}")
    public String devolverLibro(@PathVariable Long id, Model model) {
        try {
            libroService.devolverLibro(id);
            model.addAttribute("mensaje", "Libro devuelto exitosamente");
            return "redirect:/libros/detalle/" + id;
            
        } catch (Exception e) {
            model.addAttribute("error", "Error al devolver el libro: " + e.getMessage());
            return "redirect:/libros/detalle/" + id;
        }
    }
}
