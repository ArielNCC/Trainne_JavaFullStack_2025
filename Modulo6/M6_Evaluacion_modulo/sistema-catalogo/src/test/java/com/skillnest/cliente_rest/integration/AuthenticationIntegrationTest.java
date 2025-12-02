package com.skillnest.cliente_rest.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests de integración para verificar la autenticación con JWT
 * Verifica que admin y usuario1 pueden autenticarse correctamente
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class AuthenticationIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testLoginComoAdmin() throws Exception {
        // Credenciales del admin (definidas en el script SQL)
        Map<String, String> loginRequest = Map.of(
            "usuario", "admin",
            "password", "admin123"
        );

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.usuario").value("admin"))
                .andExpect(jsonPath("$.rol").value("ADMIN"));
    }

    @Test
    void testLoginComoUsuario1() throws Exception {
        // Credenciales del usuario1 (definidas en el script SQL)
        Map<String, String> loginRequest = Map.of(
            "usuario", "usuario1",
            "password", "usuario123"
        );

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.usuario").value("usuario1"))
                .andExpect(jsonPath("$.rol").value("USER"));
    }

    @Test
    void testLoginConCredencialesIncorrectas() throws Exception {
        Map<String, String> loginRequest = Map.of(
            "usuario", "admin",
            "password", "wrongpassword"
        );

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testLoginConUsuarioNoExistente() throws Exception {
        Map<String, String> loginRequest = Map.of(
            "usuario", "usuarioInexistente",
            "password", "anypassword"
        );

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testAccesoSinAutenticacionAEndpointProtegido() throws Exception {
        // Intentar crear producto sin token JWT
        Map<String, Object> producto = Map.of(
            "nombre", "Producto Test",
            "descripcion", "Descripción test",
            "precio", 99.99,
            "stockDisponible", 10
        );

        mockMvc.perform(post("/api/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(producto)))
                .andExpect(status().isForbidden());
    }

    @Test
    void testAccesoConTokenValido() throws Exception {
        // Primero hacer login para obtener token
        Map<String, String> loginRequest = Map.of(
            "usuario", "admin",
            "password", "admin123"
        );

        String response = mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Extraer token (en un test real deberías usar una librería JSON)
        String token = objectMapper.readTree(response).get("token").asText();

        // Crear producto con token válido
        Map<String, Object> producto = Map.of(
            "nombre", "Producto Test Auth",
            "descripcion", "Descripción test con auth",
            "precio", 99.99,
            "stockDisponible", 10
        );

        mockMvc.perform(post("/api/productos")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(producto)))
                .andExpect(status().isCreated());
    }

    @Test
    void testAccesoConTokenInvalido() throws Exception {
        Map<String, Object> producto = Map.of(
            "nombre", "Producto Test",
            "descripcion", "Descripción test",
            "precio", 99.99,
            "stockDisponible", 10
        );

        mockMvc.perform(post("/api/productos")
                .header("Authorization", "Bearer tokeninvalido")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(producto)))
                .andExpect(status().isForbidden());
    }
}