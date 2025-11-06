package com.skillnest.web.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skillnest.web.models.Libro;
import com.skillnest.web.models.LibroDTO;
import com.skillnest.web.repositories.LibroDao;
import com.skillnest.web.repositories.LibroRepository;

/**
 * Servicio para gestión de Libros
 * Implementa operaciones con JPA (LibroRepository) y JDBC (LibroDao)
 * Usa @Transactional para garantizar consistencia de datos
 */
@Service
public class LibroService {

    // Repositorio JPA
    @Autowired
    private LibroRepository libroRepository;

    // DAO JDBC
    @Autowired
    private LibroDao libroDao;

    // ==================== Operaciones con JPA ====================

    /**
     * Obtener todos los libros (JPA)
     */
    public List<Libro> listarTodos() {
        return libroRepository.findAll();
    }

    /**
     * Obtener todos los libros con sus autores (JPA)
     */
    public List<Libro> listarTodosConAutor() {
        return libroRepository.findAllWithAutor();
    }

    /**
     * Obtener libro por ID (JPA)
     */
    public Libro buscarPorId(Long id) {
        return libroRepository.findById(id).orElse(null);
    }

    /**
     * Obtener libro por ID con autor cargado (JPA)
     */
    public Libro buscarPorIdConAutor(Long id) {
        return libroRepository.findByIdWithAutor(id);
    }

    /**
     * Registrar un nuevo libro (JPA)
     * @Transactional asegura que la operación sea atómica
     */
    @Transactional
    public Libro registrarLibro(Libro libro) {
        return libroRepository.save(libro);
    }

    /**
     * Actualizar un libro existente (JPA)
     */
    @Transactional
    public Libro actualizarLibro(Libro libro) {
        if (libroRepository.existsById(libro.getId())) {
            return libroRepository.save(libro);
        }
        throw new RuntimeException("Libro no encontrado con ID: " + libro.getId());
    }

    /**
     * Eliminar un libro (JPA)
     */
    @Transactional
    public void eliminarLibro(Long id) {
        if (libroRepository.existsById(id)) {
            libroRepository.deleteById(id);
        } else {
            throw new RuntimeException("Libro no encontrado con ID: " + id);
        }
    }

    /**
     * Buscar libros por título (JPA)
     */
    public List<Libro> buscarPorTitulo(String titulo) {
        return libroRepository.findByTituloContainingIgnoreCase(titulo);
    }

    /**
     * Buscar libro por ISBN (JPA)
     */
    public Libro buscarPorIsbn(String isbn) {
        return libroRepository.findByIsbn(isbn);
    }

    /**
     * Buscar libros por autor (JPA)
     */
    public List<Libro> buscarPorAutor(Long autorId) {
        return libroRepository.findByAutorId(autorId);
    }

    /**
     * Obtener libros disponibles (JPA)
     */
    public List<Libro> listarDisponibles() {
        return libroRepository.findLibrosDisponibles();
    }

    /**
     * Prestar un libro - reduce la cantidad disponible
     * @Transactional asegura consistencia en caso de error
     */
    @Transactional
    public void prestarLibro(Long libroId) {
        Libro libro = libroRepository.findById(libroId)
            .orElseThrow(() -> new RuntimeException("Libro no encontrado"));
        
        if (libro.getCantidadDisponible() <= 0) {
            throw new RuntimeException("No hay ejemplares disponibles para préstamo");
        }
        
        libro.setCantidadDisponible(libro.getCantidadDisponible() - 1);
        libroRepository.save(libro);
    }

    /**
     * Devolver un libro - aumenta la cantidad disponible
     * @Transactional asegura consistencia en caso de error
     */
    @Transactional
    public void devolverLibro(Long libroId) {
        Libro libro = libroRepository.findById(libroId)
            .orElseThrow(() -> new RuntimeException("Libro no encontrado"));
        
        if (libro.getCantidadDisponible() >= libro.getCantidadTotal()) {
            throw new RuntimeException("Ya están todos los ejemplares en biblioteca");
        }
        
        libro.setCantidadDisponible(libro.getCantidadDisponible() + 1);
        libroRepository.save(libro);
    }

    // ==================== Operaciones con JDBC ====================

    /**
     * Obtener todos los libros (JDBC)
     */
    public List<LibroDTO> listarTodosJdbc() {
        return libroDao.obtenerTodos();
    }

    /**
     * Obtener libro por ID (JDBC)
     */
    public LibroDTO buscarPorIdJdbc(Long id) {
        return libroDao.obtenerPorId(id);
    }

    /**
     * Registrar un nuevo libro (JDBC)
     */
    @Transactional
    public int registrarLibroJdbc(LibroDTO libro) {
        return libroDao.insertar(libro);
    }

    /**
     * Actualizar un libro (JDBC)
     */
    @Transactional
    public int actualizarLibroJdbc(LibroDTO libro) {
        return libroDao.actualizar(libro);
    }

    /**
     * Eliminar un libro (JDBC)
     */
    @Transactional
    public int eliminarLibroJdbc(Long id) {
        return libroDao.eliminar(id);
    }

    /**
     * Buscar libros por título (JDBC)
     */
    public List<LibroDTO> buscarPorTituloJdbc(String titulo) {
        return libroDao.buscarPorTitulo(titulo);
    }

    /**
     * Buscar libro por ISBN (JDBC)
     */
    public LibroDTO buscarPorIsbnJdbc(String isbn) {
        return libroDao.obtenerPorIsbn(isbn);
    }

    /**
     * Buscar libros por autor (JDBC)
     */
    public List<LibroDTO> buscarPorAutorJdbc(Long autorId) {
        return libroDao.obtenerPorAutor(autorId);
    }

    /**
     * Obtener libros disponibles (JDBC)
     */
    public List<LibroDTO> listarDisponiblesJdbc() {
        return libroDao.obtenerDisponibles();
    }

    /**
     * Contar total de libros
     */
    public int contarLibros() {
        return libroDao.contarLibros();
    }
}
