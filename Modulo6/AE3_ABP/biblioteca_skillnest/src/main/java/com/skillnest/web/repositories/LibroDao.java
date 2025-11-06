package com.skillnest.web.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.skillnest.web.models.LibroDTO;
import com.skillnest.web.rowmappers.LibroRowMapper;

/**
 * DAO para Libro usando JdbcTemplate
 * Implementa operaciones CRUD con SQL directo
 */
@Repository
public class LibroDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Obtener todos los libros con información del autor
     */
    public List<LibroDTO> obtenerTodos() {
        String sql = "SELECT l.*, CONCAT(a.nombre, ' ', a.apellido) as autor_nombre " +
                     "FROM libros l " +
                     "INNER JOIN autores a ON l.autor_id = a.id " +
                     "ORDER BY l.titulo";
        return jdbcTemplate.query(sql, new LibroRowMapper());
    }

    /**
     * Obtener libro por ID
     */
    public LibroDTO obtenerPorId(Long id) {
        String sql = "SELECT l.*, CONCAT(a.nombre, ' ', a.apellido) as autor_nombre " +
                     "FROM libros l " +
                     "INNER JOIN autores a ON l.autor_id = a.id " +
                     "WHERE l.id = ?";
        return jdbcTemplate.queryForObject(sql, new LibroRowMapper(), id);
    }

    /**
     * Obtener libro por ISBN
     */
    public LibroDTO obtenerPorIsbn(String isbn) {
        String sql = "SELECT l.*, CONCAT(a.nombre, ' ', a.apellido) as autor_nombre " +
                     "FROM libros l " +
                     "INNER JOIN autores a ON l.autor_id = a.id " +
                     "WHERE l.isbn = ?";
        return jdbcTemplate.queryForObject(sql, new LibroRowMapper(), isbn);
    }

    /**
     * Obtener libros por autor
     */
    public List<LibroDTO> obtenerPorAutor(Long autorId) {
        String sql = "SELECT l.*, CONCAT(a.nombre, ' ', a.apellido) as autor_nombre " +
                     "FROM libros l " +
                     "INNER JOIN autores a ON l.autor_id = a.id " +
                     "WHERE l.autor_id = ? " +
                     "ORDER BY l.titulo";
        return jdbcTemplate.query(sql, new LibroRowMapper(), autorId);
    }

    /**
     * Buscar libros por título
     */
    public List<LibroDTO> buscarPorTitulo(String titulo) {
        String sql = "SELECT l.*, CONCAT(a.nombre, ' ', a.apellido) as autor_nombre " +
                     "FROM libros l " +
                     "INNER JOIN autores a ON l.autor_id = a.id " +
                     "WHERE l.titulo LIKE ? " +
                     "ORDER BY l.titulo";
        return jdbcTemplate.query(sql, new LibroRowMapper(), "%" + titulo + "%");
    }

    /**
     * Insertar un nuevo libro
     */
    public int insertar(LibroDTO libro) {
        String sql = "INSERT INTO libros (titulo, isbn, anio_publicacion, genero, editorial, " +
                     "numero_paginas, cantidad_disponible, cantidad_total, autor_id) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, 
            libro.getTitulo(),
            libro.getIsbn(),
            libro.getAnioPublicacion(),
            libro.getGenero(),
            libro.getEditorial(),
            libro.getNumeroPaginas(),
            libro.getCantidadDisponible(),
            libro.getCantidadTotal(),
            libro.getAutorId()
        );
    }

    /**
     * Actualizar un libro existente
     */
    public int actualizar(LibroDTO libro) {
        String sql = "UPDATE libros SET titulo = ?, isbn = ?, anio_publicacion = ?, genero = ?, " +
                     "editorial = ?, numero_paginas = ?, cantidad_disponible = ?, cantidad_total = ?, " +
                     "autor_id = ? WHERE id = ?";
        return jdbcTemplate.update(sql, 
            libro.getTitulo(),
            libro.getIsbn(),
            libro.getAnioPublicacion(),
            libro.getGenero(),
            libro.getEditorial(),
            libro.getNumeroPaginas(),
            libro.getCantidadDisponible(),
            libro.getCantidadTotal(),
            libro.getAutorId(),
            libro.getId()
        );
    }

    /**
     * Eliminar un libro por ID
     */
    public int eliminar(Long id) {
        String sql = "DELETE FROM libros WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    /**
     * Actualizar disponibilidad de libro (para préstamos)
     */
    public int actualizarDisponibilidad(Long id, int cantidadDisponible) {
        String sql = "UPDATE libros SET cantidad_disponible = ? WHERE id = ?";
        return jdbcTemplate.update(sql, cantidadDisponible, id);
    }

    /**
     * Contar total de libros
     */
    public int contarLibros() {
        String sql = "SELECT COUNT(*) FROM libros";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    /**
     * Obtener libros disponibles
     */
    public List<LibroDTO> obtenerDisponibles() {
        String sql = "SELECT l.*, CONCAT(a.nombre, ' ', a.apellido) as autor_nombre " +
                     "FROM libros l " +
                     "INNER JOIN autores a ON l.autor_id = a.id " +
                     "WHERE l.cantidad_disponible > 0 " +
                     "ORDER BY l.titulo";
        return jdbcTemplate.query(sql, new LibroRowMapper());
    }
}
