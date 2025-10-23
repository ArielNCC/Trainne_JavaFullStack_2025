package com.skillnest.agendacontactos.controller;

import com.skillnest.agendacontactos.model.Contacto;
import com.skillnest.agendacontactos.service.ContactoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/contactos")
public class ContactoController {
    @Autowired
    private ContactoService contactoService;

    // Acción para mostrar el formulario
    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("contacto", new Contacto());
        model.addAttribute("accion", "Nuevo");
        return "contactos/formulario";
    }

    // Acción para recibir el formulario (POST)
    @PostMapping("/nuevo")
    public String registrarContacto(@ModelAttribute Contacto contacto, Model model) {
        try {
            // Validación básica
            if (contacto.getNombre() == null || contacto.getNombre().trim().isEmpty()) {
                model.addAttribute("error", "El nombre es obligatorio");
                model.addAttribute("contacto", contacto);
                model.addAttribute("accion", "Nuevo");
                return "contactos/formulario";
            }
            
            if (contacto.getCorreo() == null || contacto.getCorreo().trim().isEmpty()) {
                model.addAttribute("error", "El correo es obligatorio");
                model.addAttribute("contacto", contacto);
                model.addAttribute("accion", "Nuevo");
                return "contactos/formulario";
            }
            
            if (contacto.getTelefono() == null || contacto.getTelefono().trim().isEmpty()) {
                model.addAttribute("error", "El teléfono es obligatorio");
                model.addAttribute("contacto", contacto);
                model.addAttribute("accion", "Nuevo");
                return "contactos/formulario";
            }
            
            // Validar que el correo no exista
            if (contactoService.existeEmail(contacto.getCorreo(), null)) {
                model.addAttribute("error", "Ya existe un contacto con este correo electrónico");
                model.addAttribute("contacto", contacto);
                model.addAttribute("accion", "Nuevo");
                return "contactos/formulario";
            }
            
            contactoService.crearContacto(contacto);
            return "redirect:/contactos/listar?success=true";
        } catch (Exception e) {
            model.addAttribute("error", "Error al registrar el contacto: " + e.getMessage());
            model.addAttribute("contacto", contacto);
            model.addAttribute("accion", "Nuevo");
            return "contactos/formulario";
        }
    }

    // Acción para listar contactos registrados
    @GetMapping("/listar")
    public String listarContactos(Model model, 
                                  @RequestParam(required = false) String success,
                                  @RequestParam(required = false) String deleted,
                                  @RequestParam(required = false) String updated,
                                  @RequestParam(required = false) String error,
                                  @RequestParam(required = false) String buscar) {
        
        List<Contacto> contactos;
        
        // Si hay búsqueda, filtrar contactos
        if (buscar != null && !buscar.trim().isEmpty()) {
            contactos = contactoService.buscarContactosPorNombre(buscar.trim());
            model.addAttribute("mensajeBusqueda", "Resultados para: \"" + buscar + "\"");
            model.addAttribute("terminoBusqueda", buscar);
        } else {
            contactos = contactoService.obtenerTodosLosContactos();
        }
        
        model.addAttribute("contactos", contactos);
        model.addAttribute("totalContactos", contactos.size());
        
        // Mensajes de éxito
        if ("true".equals(success)) {
            model.addAttribute("mensaje", "Contacto registrado exitosamente");
            model.addAttribute("tipoMensaje", "success");
        } else if ("true".equals(deleted)) {
            model.addAttribute("mensaje", "Contacto eliminado exitosamente");
            model.addAttribute("tipoMensaje", "success");
        } else if ("true".equals(updated)) {
            model.addAttribute("mensaje", "Contacto actualizado exitosamente");
            model.addAttribute("tipoMensaje", "success");
        }
        
        // Mensajes de error
        if ("not-found".equals(error)) {
            model.addAttribute("mensaje", "El contacto no fue encontrado");
            model.addAttribute("tipoMensaje", "danger");
        } else if ("delete-failed".equals(error)) {
            model.addAttribute("mensaje", "Error al eliminar el contacto");
            model.addAttribute("tipoMensaje", "danger");
        }
        
        return "contactos/listar";
    }
    
    // Acción para ver detalle de un contacto
    @GetMapping("/{id}")
    public String verDetalle(@PathVariable Long id, Model model) {
        var contacto = contactoService.obtenerContactoPorId(id);
        if (contacto.isPresent()) {
            model.addAttribute("contacto", contacto.get());
            return "contactos/detalle";
        } else {
            return "redirect:/contactos/listar?error=not-found";
        }
    }
    
    // Acción para eliminar un contacto
    @PostMapping("/{id}/eliminar")
    public String eliminarContacto(@PathVariable Long id) {
        try {
            if (contactoService.eliminarContacto(id)) {
                return "redirect:/contactos/listar?deleted=true";
            } else {
                return "redirect:/contactos/listar?error=not-found";
            }
        } catch (Exception e) {
            return "redirect:/contactos/listar?error=delete-failed";
        }
    }
    
    // Acción para mostrar formulario de edición
    @GetMapping("/{id}/editar")
    public String mostrarFormularioEdicion(@PathVariable Long id, Model model) {
        var contacto = contactoService.obtenerContactoPorId(id);
        if (contacto.isPresent()) {
            model.addAttribute("contacto", contacto.get());
            model.addAttribute("accion", "Editar");
            return "contactos/formulario";
        } else {
            return "redirect:/contactos/listar?error=not-found";
        }
    }
    
    // Acción para actualizar un contacto
    @PostMapping("/{id}/editar")
    public String actualizarContacto(@PathVariable Long id, @ModelAttribute Contacto contacto, Model model) {
        try {
            // Validación básica
            if (contacto.getNombre() == null || contacto.getNombre().trim().isEmpty()) {
                model.addAttribute("error", "El nombre es obligatorio");
                model.addAttribute("contacto", contacto);
                model.addAttribute("accion", "Editar");
                return "contactos/formulario";
            }
            
            if (contacto.getCorreo() == null || contacto.getCorreo().trim().isEmpty()) {
                model.addAttribute("error", "El correo es obligatorio");
                model.addAttribute("contacto", contacto);
                model.addAttribute("accion", "Editar");
                return "contactos/formulario";
            }
            
            if (contacto.getTelefono() == null || contacto.getTelefono().trim().isEmpty()) {
                model.addAttribute("error", "El teléfono es obligatorio");
                model.addAttribute("contacto", contacto);
                model.addAttribute("accion", "Editar");
                return "contactos/formulario";
            }
            
            // Validar que el correo no exista (excluyendo el contacto actual)
            if (contactoService.existeEmail(contacto.getCorreo(), id)) {
                model.addAttribute("error", "Ya existe un contacto con este correo electrónico");
                model.addAttribute("contacto", contacto);
                model.addAttribute("accion", "Editar");
                return "contactos/formulario";
            }
            
            var contactoActualizado = contactoService.actualizarContacto(id, contacto);
            if (contactoActualizado.isPresent()) {
                return "redirect:/contactos/listar?updated=true";
            } else {
                return "redirect:/contactos/listar?error=not-found";
            }
        } catch (Exception e) {
            model.addAttribute("error", "Error al actualizar el contacto: " + e.getMessage());
            model.addAttribute("contacto", contacto);
            model.addAttribute("accion", "Editar");
            return "contactos/formulario";
        }
    }
}
