package com.skillnest.m6_ae1.control;

import com.skillnest.m6_ae1.model.Usuario;
 
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
 
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
 

@Controller
public class AgregarUsuarioCommand {

	private final ServletContext servletContext;

	public AgregarUsuarioCommand(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	@GetMapping("/")
	public String root() {
		return "redirect:/usuario/form";
	}

	@GetMapping("/usuario/form")
	public String mostrarFormulario(Model model) {
		model.addAttribute("usuario", new Usuario());
		return "form";
	}

	@PostMapping("/usuario/agregar")
	public String agregarUsuario(@Valid Usuario usuario, BindingResult result, 
	                           HttpServletRequest request, RedirectAttributes redirectAttributes, Model model) {
		if (result.hasErrors()) {
			return "form"; // Volver al formulario con errores
		}
		
		// Obtener o crear lista de usuarios
		List<Usuario> usuarios = obtenerUsuarios(request);
		usuarios.add(usuario);
		
		redirectAttributes.addFlashAttribute("mensaje", "Usuario agregado exitosamente");
		return "redirect:/usuario/ver";
	}

	@SuppressWarnings("unchecked")
	private List<Usuario> obtenerUsuarios(HttpServletRequest request) {
		List<Usuario> usuarios = (List<Usuario>) servletContext.getAttribute("servletUsuarios");
		if (usuarios == null) {
			usuarios = new ArrayList<>();
			servletContext.setAttribute("servletUsuarios", usuarios);
		}
		return usuarios;
	}

	// POST is handled by the UsuarioServlet; the controller keeps the form display and viewing

	@GetMapping("/usuario/ver")
	public String verUsuarios(Model model) {
	// get users from servlet context only
	var list = new java.util.ArrayList<Usuario>();
		// get users stored by servlet
		Object attr = servletContext.getAttribute("servletUsuarios");
		if (attr instanceof java.util.List) {
			java.util.List<?> servletList = (java.util.List<?>) attr;
			for (Object o : servletList) {
				if (o instanceof Usuario) {
					list.add((Usuario) o);
				}
			}
		}
		model.addAttribute("usuarios", list);
		return "usuarios";
	}

}
