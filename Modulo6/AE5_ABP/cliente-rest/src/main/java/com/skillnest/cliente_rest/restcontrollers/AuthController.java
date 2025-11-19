package com.skillnest.cliente_rest.restcontrollers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skillnest.cliente_rest.model.Usuario;
import com.skillnest.cliente_rest.security.JwtUtil;
import com.skillnest.cliente_rest.service.DebugService;
import com.skillnest.cliente_rest.service.UsuarioService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private DebugService debugService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credenciales) {
        String usuario = credenciales.get("usuario");
        String password = credenciales.get("password");

        // DEBUG: Añadir logs para diagnosticar
        debugService.debugCredenciales(usuario, password);

        // Validar credenciales contra base de datos
        if (usuarioService.validarCredenciales(usuario, password)) {
            Usuario user = usuarioService.buscarPorNombreUsuario(usuario);
            String token = jwtUtil.generarToken(usuario);
            return ResponseEntity.ok(Map.of(
                "token", token,
                "usuario", user.getNombreUsuario(),
                "email", user.getEmail(),
                "rol", user.getRol()
            ));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(Map.of("error", "Credenciales inválidas"));
    }
    
    // Endpoint temporal para generar hash de contraseña (eliminar en producción)
    @PostMapping("/hash")
    public ResponseEntity<?> generarHash(@RequestBody Map<String, String> body) {
        String password = body.get("password");
        String hash = passwordEncoder.encode(password);
        return ResponseEntity.ok(Map.of(
            "password", password,
            "hash", hash
        ));
    }
}