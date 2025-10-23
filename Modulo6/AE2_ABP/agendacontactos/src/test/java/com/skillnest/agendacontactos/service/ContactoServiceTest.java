package com.skillnest.agendacontactos.service;

import com.skillnest.agendacontactos.model.Contacto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ContactoServiceTest {

    @Autowired
    private ContactoService contactoService;

    private Contacto contactoPrueba;

    @BeforeEach
    void setUp() {
        contactoPrueba = new Contacto("Test User", "test@example.com", "+56999999999");
    }

    @Test
    void testCrearContacto() {
        Contacto contactoCreado = contactoService.crearContacto(contactoPrueba);
        assertNotNull(contactoCreado.getId());
        assertEquals("Test User", contactoCreado.getNombre());
        assertEquals("test@example.com", contactoCreado.getCorreo());
        assertEquals("+56999999999", contactoCreado.getTelefono());
    }

    @Test
    void testObtenerTodosLosContactos() {
        contactoService.crearContacto(contactoPrueba);
        List<Contacto> contactos = contactoService.obtenerTodosLosContactos();
        assertTrue(contactos.size() > 0);
    }

    @Test
    void testBuscarContactosPorNombre() {
        contactoService.crearContacto(contactoPrueba);
        List<Contacto> encontrados = contactoService.buscarContactosPorNombre("Test");
        assertFalse(encontrados.isEmpty());
        assertEquals("Test User", encontrados.get(0).getNombre());
    }

    @Test
    void testExisteEmail() {
        contactoService.crearContacto(contactoPrueba);
        assertTrue(contactoService.existeEmail("test@example.com", null));
        assertFalse(contactoService.existeEmail("noexiste@example.com", null));
    }
}
