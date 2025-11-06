package com.skillnest.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.skillnest.web.Dto.UserDto;
import com.skillnest.web.models.Usuario;
import com.skillnest.web.services.UsuarioServiceImpl;

import jakarta.validation.Valid;

@Controller
public class WebController {
	
	@Autowired
	UsuarioServiceImpl usuarioServiceImpl;

	@GetMapping("/")
	public String index() {
		return "redirect:/login";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/registro")
	public String mostrarRegistroForm(Model model) {
		UserDto user = new UserDto();
		model.addAttribute("usuario", user);
		return "registro";
	}
	
    @PostMapping("/registro/guardar")
    public String registroGuardar(@Valid @ModelAttribute("usuario") UserDto userDto,
                              BindingResult result,
                              Model model) {
        Usuario existe_usuario = usuarioServiceImpl.findByEmail(userDto.getEmail());

        if (existe_usuario != null && existe_usuario.getEmail() != null && !existe_usuario.getEmail().isEmpty()) {
            result.rejectValue("email", null,
                    "Ya existe una cuenta registrada con el mismo correo electrónico");
        }

        if (result.hasErrors()) {
            model.addAttribute("usuario", userDto);
            return "registro";
        }

        usuarioServiceImpl.saveUser(userDto);
        return "redirect:/login?registro=success";
    }
    
	@GetMapping("/admin/detalle")
	public String detalleAdmin(Model model) {
		model.addAttribute("usuarios", usuarioServiceImpl.findAllUsers());
		return "admin";
	}
	
	@GetMapping("/perfil/detalle")
	public String detallePerfil(Model model) {
		// Obtener el usuario autenticado
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		
		// Buscar el usuario en la base de datos
		Usuario usuario = usuarioServiceImpl.findByUsername(username);
		
		if (usuario != null) {
			// Convertir a UserDto para pasar a la vista
			UserDto userDto = new UserDto();
			userDto.setId(usuario.getId());
			userDto.setUsername(usuario.getUsername());
			userDto.setEmail(usuario.getEmail());
			userDto.setRole(usuario.getRole());
			model.addAttribute("usuario", userDto);
		}
		
		return "perfil";
	}
	
	@GetMapping("/panel")
	public String panel(Model model) {
		// Obtener el usuario autenticado
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		model.addAttribute("username", username);
		
		return "panel";
	}
}
