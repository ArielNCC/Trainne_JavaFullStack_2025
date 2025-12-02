package com.skillnest.cliente_rest.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillnest.cliente_rest.model.Producto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests de integración para la API de productos
 * Verifica el funcionamiento completo del controlador REST con autenticación
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class ProductoRestControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Producto productoTest;

    @BeforeEach
    void setUp() {
        // Crear producto de prueba
        productoTest = new Producto();
        productoTest.setNombre("Producto Test");
        productoTest.setDescripcion("Descripción del producto de prueba");
        productoTest.setPrecio(BigDecimal.valueOf(99.99));
        productoTest.setStockDisponible(50);
    }

    @Test
    void testObtenerProductosSinAutenticacion() throws Exception {
        // Debería permitir obtener productos sin autenticación
        mockMvc.perform(get("/api/productos")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testObtenerProductosConPaginacion() throws Exception {
        mockMvc.perform(get("/api/productos")
                .param("page", "0")
                .param("size", "5")
                .param("sort", "nombre")
                .param("direction", "asc")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productos").isArray())
                .andExpect(jsonPath("$.currentPage").value(0))
                .andExpect(jsonPath("$.size").value(5));
    }

    @Test
    void testObtenerTodosLosProductos() throws Exception {
        mockMvc.perform(get("/api/productos/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testBuscarProductosPorNombre() throws Exception {
        mockMvc.perform(get("/api/productos/buscar")
                .param("nombre", "Teclado")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productos").isArray())
                .andExpect(jsonPath("$.termino").value("Teclado"));
    }

    @Test
    void testObtenerProductosConStock() throws Exception {
        mockMvc.perform(get("/api/productos/con-stock"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productos").isArray())
                .andExpect(jsonPath("$.total").isNumber());
    }

    @Test
    @WithMockUser(roles = "USER")
    void testCrearProductoComoUsuarioNormal() throws Exception {
        // Los usuarios normales no deberían poder crear productos
        mockMvc.perform(post("/api/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productoTest)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testCrearProductoComoAdmin() throws Exception {
        // Los administradores deberían poder crear productos
        mockMvc.perform(post("/api/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productoTest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.mensaje").value("Producto creado exitosamente"))
                .andExpect(jsonPath("$.producto.nombre").value("Producto Test"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testCrearProductoConDatosInvalidos() throws Exception {
        // Producto con datos inválidos (precio negativo)
        productoTest.setPrecio(BigDecimal.valueOf(-10));
        
        mockMvc.perform(post("/api/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productoTest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Datos inválidos"));
    }

    @Test
    void testObtenerProductoPorIdExistente() throws Exception {
        // Assuming producto with ID 1 exists
        mockMvc.perform(get("/api/productos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").exists());
    }

    @Test
    void testObtenerProductoPorIdNoExistente() throws Exception {
        mockMvc.perform(get("/api/productos/99999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Producto no encontrado"))
                .andExpect(jsonPath("$.id").value(99999));
    }

    @Test
    @WithMockUser(roles = "USER")
    void testActualizarProductoComoUsuarioNormal() throws Exception {
        // Los usuarios normales no deberían poder actualizar productos
        mockMvc.perform(put("/api/productos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productoTest)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testActualizarProductoComoAdmin() throws Exception {
        // Primero obtener un producto existente para actualizar
        productoTest.setNombre("Producto Actualizado");
        
        mockMvc.perform(put("/api/productos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productoTest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensaje").value("Producto actualizado exitosamente"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testActualizarStockComoAdmin() throws Exception {
        mockMvc.perform(patch("/api/productos/1/stock")
                .param("stock", "100")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensaje").value("Stock actualizado exitosamente"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testActualizarStockConValorInvalido() throws Exception {
        mockMvc.perform(patch("/api/productos/1/stock")
                .param("stock", "-5")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Stock inválido"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void testEliminarProductoComoUsuarioNormal() throws Exception {
        // Los usuarios normales no deberían poder eliminar productos
        mockMvc.perform(delete("/api/productos/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testEliminarProductoNoExistente() throws Exception {
        mockMvc.perform(delete("/api/productos/99999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Producto no encontrado"));
    }
}