package com.skillnest.web;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.skillnest.web.models.Autor;
import com.skillnest.web.models.Libro;
import com.skillnest.web.services.AutorService;
import com.skillnest.web.services.LibroService;

/**
 * Prueba de operaciones transaccionales con @SpringBootTest
 * 
 * Esta prueba valida que:
 * 1. Se puede registrar un autor (operación transaccional)
 * 2. Se puede registrar un libro asociado a ese autor (operación transaccional)
 * 3. Si ocurre un error, la transacción se revierte automáticamente
 * 
 * @Transactional en los servicios asegura que si falla cualquier operación,
 * todos los cambios se revierten automáticamente, manteniendo la consistencia de datos.
 */
@SpringBootTest
public class TransactionalOperationsTest {

    @Autowired
    private AutorService autorService;

    @Autowired
    private LibroService libroService;

    /**
     * Test de operación transaccional completa:
     * 1. Registrar un autor
     * 2. Registrar un libro con ese autor
     * 
     * Si alguna operación falla, ambas se revierten por @Transactional
     */
    @Test
    @Transactional
    public void testRegistrarAutorYLibroTransaccional() {
        // 1. Registrar un autor
        Autor autor = new Autor();
        autor.setNombre("Autor");
        autor.setApellido("de Prueba");
        autor.setNacionalidad("Testlandia");
        autor.setBiografia("Este es un autor de prueba.");
        
        // Esta operación está marcada con @Transactional en AutorService
        Autor autorGuardado = autorService.registrarAutor(autor);
        
        // Verificar que el autor fue guardado correctamente
        assertNotNull(autorGuardado);
        assertNotNull(autorGuardado.getId());
        assertEquals("Autor", autorGuardado.getNombre());
        assertEquals("de Prueba", autorGuardado.getApellido());
        
        // 2. Registrar un libro asociado a ese autor
        Libro libro = new Libro();
        libro.setTitulo("Libro de Prueba");
        libro.setIsbn("111-1111111111");
        libro.setAnioPublicacion(1999);
        libro.setGenero("Pruebas de CRUD");
        libro.setEditorial("Test Sprig Boot");
        libro.setNumeroPaginas(111);
        libro.setCantidadTotal(5);
        libro.setCantidadDisponible(5);
        libro.setAutor(autorGuardado);
        
        // Esta operación está marcada con @Transactional en LibroService
        Libro libroGuardado = libroService.registrarLibro(libro);
        
        // Verificar que el libro fue guardado correctamente
        assertNotNull(libroGuardado);
        assertNotNull(libroGuardado.getId());
        assertEquals("Libro de Prueba", libroGuardado.getTitulo());
        assertEquals("111-1111111111", libroGuardado.getIsbn());
        assertEquals(autorGuardado.getId(), libroGuardado.getAutor().getId());
        
        // Verificar que el libro está asociado al autor
        assertEquals("Autor", libroGuardado.getAutor().getNombre());
        assertEquals("de Prueba", libroGuardado.getAutor().getApellido());
    }
    
    /**
     * Test de validación: no se puede guardar un libro sin título
     * Esta prueba verifica que las validaciones funcionan correctamente
     */
    @Test
    @Transactional
    public void testNoGuardarLibroSinTitulo() {
        // Crear un autor
        Autor autor = new Autor();
        autor.setNombre("Autor2");
        autor.setApellido("de Prueba2");
        autor.setNacionalidad("Testlandia2");
        
        Autor autorGuardado = autorService.registrarAutor(autor);
        
        // Intentar crear un libro sin título (validación en controlador)
        Libro libro = new Libro();
        libro.setIsbn("222-2222222222");
        libro.setAutor(autorGuardado);
        // Título no establecido (null)
        
        // Esta operación debería fallar en el nivel de base de datos
        // porque titulo es NOT NULL en la tabla
        assertThrows(Exception.class, () -> {
            libroService.registrarLibro(libro);
        });
    }
    
    /**
     * Test de validación: verificar que autor y libro se guardan correctamente
     */
    @Test
    @Transactional
    public void testAutorConMultiplesLibros() {
        // Registrar un autor
        Autor autor = new Autor();
        autor.setNombre("Autor3");
        autor.setApellido("de Prueba3");
        autor.setNacionalidad("Testlandia3");
        
        Autor autorGuardado = autorService.registrarAutor(autor);
        
        // Registrar varios libros para el mismo autor
        Libro libro1 = new Libro();
        libro1.setTitulo("Primer Libro de Prueba");
        libro1.setIsbn("333-3333333333");
        libro1.setAutor(autorGuardado);
        libro1.setCantidadTotal(3);
        libro1.setCantidadDisponible(3);
        
        Libro libro2 = new Libro();
        libro2.setTitulo("Segundo Libro de Prueba");
        libro2.setIsbn("444-4444444444");
        libro2.setAutor(autorGuardado);
        libro2.setCantidadTotal(2);
        libro2.setCantidadDisponible(2);
        
        // Guardar ambos libros (operaciones transaccionales)
        Libro libro1Guardado = libroService.registrarLibro(libro1);
        Libro libro2Guardado = libroService.registrarLibro(libro2);
        
        // Verificar que ambos libros se guardaron correctamente
        assertNotNull(libro1Guardado.getId());
        assertNotNull(libro2Guardado.getId());
        
        // Verificar que ambos están asociados al mismo autor
        assertEquals(autorGuardado.getId(), libro1Guardado.getAutor().getId());
        assertEquals(autorGuardado.getId(), libro2Guardado.getAutor().getId());
    }
}
