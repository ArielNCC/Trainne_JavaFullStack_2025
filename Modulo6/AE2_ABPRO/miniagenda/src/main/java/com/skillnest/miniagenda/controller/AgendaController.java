package com.skillnest.miniagenda.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.skillnest.miniagenda.controller.command.AgregarEventoCommand;
import com.skillnest.miniagenda.controller.command.ModificarEventoCommand;
import com.skillnest.miniagenda.model.Evento;
import com.skillnest.miniagenda.service.AgendaService;

import jakarta.validation.Valid;

/**
 * Controlador principal de la agenda
 * 
 * @Controller: Marca esta clase como un controlador de Spring MVC
 * @RequestMapping: Define la ruta base para todos los endpoints de este controlador
 */
@Controller
@RequestMapping("/agenda")
public class AgendaController {

    private static final Logger logger = LoggerFactory.getLogger(AgendaController.class);
    
    /**
     * @Autowired: Inyección automática de dependencias
     * Spring busca un Bean del tipo AgendaService y lo inyecta aquí
     * No es necesario crear una instancia manualmente
     */
    @Autowired
    private AgendaService agendaService;
    
    /**
     * Ruta principal - redirige a la vista de eventos
     * GET /agenda
     */
    @GetMapping
    public String index() {
        return "redirect:/agenda/eventos";
    }
    
    /**
     * Muestra el formulario para agregar un nuevo evento
     * GET /agenda/formulario
     */
    @GetMapping("/formulario")
    public String mostrarFormulario(Model model) {
        logger.debug("Mostrando formulario de nuevo evento");
        
        // Crear un command object vacío para el formulario
        model.addAttribute("comando", new AgregarEventoCommand());
        
        return "formulario";
    }
    
    /**
     * Procesa el formulario de agregar evento
     * POST /agenda/agregar
     * 
     * @Valid: Activa la validación del command object
     * BindingResult: Contiene los errores de validación
     */
    @PostMapping("/agregar")
    public String agregarEvento(
            @Valid @ModelAttribute("comando") AgregarEventoCommand comando,
            BindingResult result,
            RedirectAttributes redirectAttributes) {
        
        logger.debug("Procesando formulario de nuevo evento: {}", comando);
        
        // Si hay errores de validación, volver al formulario
        if (result.hasErrors()) {
            logger.warn("Errores de validación en el formulario: {}", result.getAllErrors());
            return "formulario";
        }
        
        // Crear el evento desde el command
        Evento evento = new Evento(
            null,
            comando.getTitulo(),
            comando.getFecha(),
            comando.getDescripcion(),
            comando.getResponsable()
        );
        
        // Guardar el evento
        agendaService.agregarEvento(evento);
        
        // Agregar mensaje de éxito
        redirectAttributes.addFlashAttribute("mensaje", "Evento agregado exitosamente");
        redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        
        logger.info("Evento agregado exitosamente: {}", evento);
        
        // Redirigir a la lista de eventos
        return "redirect:/agenda/eventos";
    }
    
    /**
     * Muestra todos los eventos agrupados por fecha
     * GET /agenda/eventos
     */
    @GetMapping("/eventos")
    public String mostrarEventos(Model model) {
        logger.debug("Mostrando vista de eventos");
        
        // Obtener eventos agrupados por fecha
        Map<LocalDate, List<Evento>> eventosAgrupados = agendaService.obtenerEventosAgrupadosPorFecha();
        
        // Obtener todos los eventos ordenados
        List<Evento> todosLosEventos = agendaService.obtenerTodosLosEventos();
        
        model.addAttribute("eventosAgrupados", eventosAgrupados);
        model.addAttribute("eventos", todosLosEventos);
        
        return "eventos";
    }
    
    /**
     * Muestra el formulario para editar un evento
     * GET /agenda/editar/{id}
     */
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        logger.debug("Mostrando formulario de edición para evento ID: {}", id);
        
        return agendaService.buscarEventoPorId(id)
            .map(evento -> {
                // Crear command desde el evento existente
                ModificarEventoCommand comando = new ModificarEventoCommand(
                    evento.getId(),
                    evento.getTitulo(),
                    evento.getFecha(),
                    evento.getDescripcion(),
                    evento.getResponsable()
                );
                
                model.addAttribute("comando", comando);
                return "editar";
            })
            .orElseGet(() -> {
                redirectAttributes.addFlashAttribute("mensaje", "Evento no encontrado");
                redirectAttributes.addFlashAttribute("tipoMensaje", "danger");
                return "redirect:/agenda/eventos";
            });
    }
    
    /**
     * Procesa el formulario de modificar evento
     * POST /agenda/modificar
     */
    @PostMapping("/modificar")
    public String modificarEvento(
            @Valid @ModelAttribute("comando") ModificarEventoCommand comando,
            BindingResult result,
            RedirectAttributes redirectAttributes) {
        
        logger.debug("Procesando formulario de modificación: {}", comando);
        
        if (result.hasErrors()) {
            logger.warn("Errores de validación en el formulario: {}", result.getAllErrors());
            return "editar";
        }
        
        // Crear evento con los datos modificados
        Evento evento = new Evento(
            comando.getId(),
            comando.getTitulo(),
            comando.getFecha(),
            comando.getDescripcion(),
            comando.getResponsable()
        );
        
        // Modificar el evento
        boolean modificado = agendaService.modificarEvento(evento);
        
        if (modificado) {
            redirectAttributes.addFlashAttribute("mensaje", "Evento modificado exitosamente");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
            logger.info("Evento modificado exitosamente: {}", evento);
        } else {
            redirectAttributes.addFlashAttribute("mensaje", "No se pudo modificar el evento");
            redirectAttributes.addFlashAttribute("tipoMensaje", "danger");
            logger.error("Error al modificar evento con ID: {}", comando.getId());
        }
        
        return "redirect:/agenda/eventos";
    }
    
    /**
     * Elimina un evento
     * GET /agenda/eliminar/{id}
     */
    @GetMapping("/eliminar/{id}")
    public String eliminarEvento(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        logger.debug("Eliminando evento con ID: {}", id);
        
        boolean eliminado = agendaService.eliminarEvento(id);
        
        if (eliminado) {
            redirectAttributes.addFlashAttribute("mensaje", "Evento eliminado exitosamente");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
            logger.info("Evento eliminado exitosamente con ID: {}", id);
        } else {
            redirectAttributes.addFlashAttribute("mensaje", "No se pudo eliminar el evento");
            redirectAttributes.addFlashAttribute("tipoMensaje", "danger");
            logger.error("Error al eliminar evento con ID: {}", id);
        }
        
        return "redirect:/agenda/eventos";
    }
}
