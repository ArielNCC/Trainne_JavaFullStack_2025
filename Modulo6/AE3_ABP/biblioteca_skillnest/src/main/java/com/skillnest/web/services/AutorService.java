package com.skillnest.web.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skillnest.web.models.Autor;
import com.skillnest.web.models.AutorDTO;
import com.skillnest.web.repositories.AutorDao;
import com.skillnest.web.repositories.AutorRepository;

/**
 * Servicio para gestión de Autores
 * Implementa operaciones con JPA (AutorRepository) y JDBC (AutorDao)
 * Usa @Transactional para garantizar consistencia de datos
 */
@Service
public class AutorService {

    // Repositorio JPA
    @Autowired
    private AutorRepository autorRepository;

    // DAO JDBC
    @Autowired
    private AutorDao autorDao;

    // ==================== Operaciones con JPA ====================

    /**
     * Obtener todos los autores (JPA)
     */
    public List<Autor> listarTodos() {
        return autorRepository.findAll();
    }

    /**
     * Obtener autor por ID (JPA)
     */
    public Autor buscarPorId(Long id) {
        return autorRepository.findById(id).orElse(null);
    }

    /**
     * Registrar un nuevo autor (JPA)
     * @Transactional asegura que la operación sea atómica
     */
    @Transactional
    public Autor registrarAutor(Autor autor) {
        return autorRepository.save(autor);
    }

    /**
     * Actualizar un autor existente (JPA)
     */
    @Transactional
    public Autor actualizarAutor(Autor autor) {
        if (autorRepository.existsById(autor.getId())) {
            return autorRepository.save(autor);
        }
        throw new RuntimeException("Autor no encontrado con ID: " + autor.getId());
    }

    /**
     * Eliminar un autor (JPA)
     */
    @Transactional
    public void eliminarAutor(Long id) {
        if (autorRepository.existsById(id)) {
            autorRepository.deleteById(id);
        } else {
            throw new RuntimeException("Autor no encontrado con ID: " + id);
        }
    }

    /**
     * Buscar autores por apellido (JPA)
     */
    public List<Autor> buscarPorApellido(String apellido) {
        return autorRepository.findByApellidoContainingIgnoreCase(apellido);
    }

    /**
     * Buscar autores por nombre completo (JPA)
     */
    public List<Autor> buscarPorNombreCompleto(String nombreCompleto) {
        return autorRepository.buscarPorNombreCompleto(nombreCompleto);
    }

    // ==================== Operaciones con JDBC ====================

    /**
     * Obtener todos los autores (JDBC)
     */
    public List<AutorDTO> listarTodosJdbc() {
        return autorDao.obtenerTodos();
    }

    /**
     * Obtener autor por ID (JDBC)
     */
    public AutorDTO buscarPorIdJdbc(Long id) {
        return autorDao.obtenerPorId(id);
    }

    /**
     * Registrar un nuevo autor (JDBC)
     */
    @Transactional
    public int registrarAutorJdbc(AutorDTO autor) {
        return autorDao.insertar(autor);
    }

    /**
     * Actualizar un autor (JDBC)
     */
    @Transactional
    public int actualizarAutorJdbc(AutorDTO autor) {
        return autorDao.actualizar(autor);
    }

    /**
     * Eliminar un autor (JDBC)
     */
    @Transactional
    public int eliminarAutorJdbc(Long id) {
        return autorDao.eliminar(id);
    }

    /**
     * Buscar autores por apellido (JDBC)
     */
    public List<AutorDTO> buscarPorApellidoJdbc(String apellido) {
        return autorDao.obtenerPorApellido(apellido);
    }

    /**
     * Contar total de autores
     */
    public int contarAutores() {
        return autorDao.contarAutores();
    }
}
