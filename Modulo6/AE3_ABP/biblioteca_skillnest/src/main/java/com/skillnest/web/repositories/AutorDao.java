package com.skillnest.web.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.skillnest.web.models.AutorDTO;
import com.skillnest.web.rowmappers.AutorRowMapper;

/**
 * DAO para Autor usando JdbcTemplate
 * Implementa operaciones CRUD con SQL directo
 */
@Repository
public class AutorDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Obtener todos los autores
     */
    public List<AutorDTO> obtenerTodos() {
        String sql = "SELECT * FROM autores ORDER BY apellido, nombre";
        return jdbcTemplate.query(sql, new AutorRowMapper());
    }

    /**
     * Obtener autor por ID
     */
    public AutorDTO obtenerPorId(Long id) {
        String sql = "SELECT * FROM autores WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new AutorRowMapper(), id);
    }

    /**
     * Obtener autores por apellido
     */
    public List<AutorDTO> obtenerPorApellido(String apellido) {
        String sql = "SELECT * FROM autores WHERE apellido LIKE ? ORDER BY nombre";
        return jdbcTemplate.query(sql, new AutorRowMapper(), "%" + apellido + "%");
    }

    /**
     * Insertar un nuevo autor
     */
    public int insertar(AutorDTO autor) {
        String sql = "INSERT INTO autores (nombre, apellido, nacionalidad, fecha_nacimiento, biografia) " +
                     "VALUES (?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, 
            autor.getNombre(), 
            autor.getApellido(), 
            autor.getNacionalidad(),
            autor.getFechaNacimiento(),
            autor.getBiografia()
        );
    }

    /**
     * Actualizar un autor existente
     */
    public int actualizar(AutorDTO autor) {
        String sql = "UPDATE autores SET nombre = ?, apellido = ?, nacionalidad = ?, " +
                     "fecha_nacimiento = ?, biografia = ? WHERE id = ?";
        return jdbcTemplate.update(sql, 
            autor.getNombre(), 
            autor.getApellido(), 
            autor.getNacionalidad(),
            autor.getFechaNacimiento(),
            autor.getBiografia(),
            autor.getId()
        );
    }

    /**
     * Eliminar un autor por ID
     */
    public int eliminar(Long id) {
        String sql = "DELETE FROM autores WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    /**
     * Contar total de autores
     */
    public int contarAutores() {
        String sql = "SELECT COUNT(*) FROM autores";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }
}
