package com.skillnest.cliente_rest;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthLoginIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testLoginAdmin_DebeRetornarTokenValido() throws Exception {
        // Preparar credenciales para admin
        String requestBody = """
            {
                "usuario": "admin",
                "password": "admin123"
            }
            """;

        // Ejecutar login
        MvcResult result = mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.usuario").value("admin"))
                .andExpect(jsonPath("$.rol").value("ADMIN"))
                .andReturn();

        // Verificar que el token no está vacío
        String responseBody = result.getResponse().getContentAsString();
        @SuppressWarnings("unchecked")
        Map<String, String> response = objectMapper.readValue(responseBody, Map.class);
        String token = response.get("token");
        
        assertNotNull(token, "El token no debe ser null");
        assertFalse(token.isEmpty(), "El token no debe estar vacío");
        assertTrue(token.split("\\.").length == 3, "El token JWT debe tener 3 partes separadas por puntos");
        
        System.out.println("✅ Test Admin Login EXITOSO");
        System.out.println("Token generado: " + token.substring(0, Math.min(50, token.length())) + "...");
    }

    @Test
    public void testLoginUsuario1_DebeRetornarTokenValido() throws Exception {
        // Preparar credenciales para usuario1
        String requestBody = """
            {
                "usuario": "usuario1",
                "password": "usuario123"
            }
            """;

        // Ejecutar login
        MvcResult result = mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.usuario").value("usuario1"))
                .andExpect(jsonPath("$.rol").value("USER"))
                .andReturn();

        // Verificar que el token no está vacío
        String responseBody = result.getResponse().getContentAsString();
        @SuppressWarnings("unchecked")
        Map<String, String> response = objectMapper.readValue(responseBody, Map.class);
        String token = response.get("token");
        
        assertNotNull(token, "El token no debe ser null");
        assertFalse(token.isEmpty(), "El token no debe estar vacío");
        assertTrue(token.split("\\.").length == 3, "El token JWT debe tener 3 partes separadas por puntos");
        
        System.out.println("✅ Test Usuario1 Login EXITOSO");
        System.out.println("Token generado: " + token.substring(0, Math.min(50, token.length())) + "...");
    }

    @Test
    public void testLoginConCredencialesInvalidas_DebeRetornar401() throws Exception {
        // Preparar credenciales incorrectas
        String requestBody = """
            {
                "usuario": "usuario1",
                "password": "passwordIncorrecto"
            }
            """;

        // Ejecutar login
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("Credenciales inválidas"));
        
        System.out.println("✅ Test Credenciales Inválidas EXITOSO");
    }

    @Test
    public void testLoginUsuarioNoExistente_DebeRetornar401() throws Exception {
        // Preparar credenciales de usuario que no existe
        String requestBody = """
            {
                "usuario": "usuarioInexistente",
                "password": "cualquierPassword"
            }
            """;

        // Ejecutar login
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("Credenciales inválidas"));
        
        System.out.println("✅ Test Usuario No Existente EXITOSO");
    }
}
