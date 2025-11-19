package com.skillnest.cliente_rest.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skillnest.cliente_rest.model.Usuario;
import com.skillnest.cliente_rest.service.UsuarioService;

// controller/UserControler.java
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
  
  @Autowired
  private UsuarioService usuarioService;
  
  @GetMapping("/perfil")
  public ResponseEntity<?> perfilUsuario() {
      Authentication auth = SecurityContextHolder.getContext().getAuthentication();
      String nombreUsuario = auth.getName();
      
      // Buscar información completa del usuario
      Usuario usuario = usuarioService.buscarPorNombreUsuario(nombreUsuario);
      
      if (usuario != null) {
          return ResponseEntity.ok(Map.of(
              "mensaje", "Token válido. Acceso autorizado.",
              "usuario", usuario.getNombreUsuario(),
              "email", usuario.getEmail(),
              "rol", usuario.getRol(),
              "roles", auth.getAuthorities(),
              "fechaCreacion", usuario.getFechaCreacion()
          ));
      }
      
      // Fallback en caso de que no se encuentre el usuario
      return ResponseEntity.ok(Map.of(
          "mensaje", "Token válido. Acceso autorizado.",
          "usuario", auth.getName(),
          "roles", auth.getAuthorities()
      ));
  }
}