package com.skillnest.web.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.skillnest.web.models.LibroDTO;

/**
 * RowMapper para mapear ResultSet a LibroDTO
 * Usado con JdbcTemplate
 */
public class LibroRowMapper implements RowMapper<LibroDTO> {
    
    @Override
    public LibroDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        LibroDTO libro = new LibroDTO();
        libro.setId(rs.getLong("id"));
        libro.setTitulo(rs.getString("titulo"));
        libro.setIsbn(rs.getString("isbn"));
        libro.setAnioPublicacion(rs.getInt("anio_publicacion"));
        libro.setGenero(rs.getString("genero"));
        libro.setEditorial(rs.getString("editorial"));
        libro.setNumeroPaginas(rs.getInt("numero_paginas"));
        libro.setCantidadDisponible(rs.getInt("cantidad_disponible"));
        libro.setCantidadTotal(rs.getInt("cantidad_total"));
        libro.setAutorId(rs.getLong("autor_id"));
        
        // Si la consulta incluye el nombre del autor (con JOIN)
        try {
            String autorNombre = rs.getString("autor_nombre");
            libro.setAutorNombre(autorNombre);
        } catch (SQLException e) {
            // La columna autor_nombre no existe en esta consulta, no hacer nada
        }
        
        return libro;
    }
}
