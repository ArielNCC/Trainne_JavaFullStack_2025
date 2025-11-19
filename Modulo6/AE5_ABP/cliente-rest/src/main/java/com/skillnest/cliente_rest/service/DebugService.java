package com.skillnest.cliente_rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.skillnest.cliente_rest.model.Usuario;

@Service
public class DebugService {
    
    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public void debugCredenciales(String nombreUsuario, String password) {
        System.out.println("=== DEBUG CREDENCIALES ===");
        System.out.println("Usuario buscado: " + nombreUsuario);
        System.out.println("Password enviado: " + password);
        
        Usuario usuario = usuarioService.buscarPorNombreUsuario(nombreUsuario);
        
        if (usuario == null) {
            System.out.println("❌ Usuario NO encontrado en base de datos");
        } else {
            System.out.println("✅ Usuario encontrado:");
            System.out.println("  - ID: " + usuario.getId());
            System.out.println("  - Nombre: " + usuario.getNombreUsuario());
            System.out.println("  - Email: " + usuario.getEmail());
            System.out.println("  - Rol: " + usuario.getRol());
            System.out.println("  - Hash almacenado: " + usuario.getContrasena());
            
            // Verificar si el password encoder funciona
            boolean matches = passwordEncoder.matches(password, usuario.getContrasena());
            System.out.println("  - PasswordEncoder.matches(): " + matches);
            
            // Generar hash para comparar
            String hashGenerado = passwordEncoder.encode(password);
            System.out.println("  - Hash que se generaría ahora: " + hashGenerado);
        }
        System.out.println("========================");
    }
}