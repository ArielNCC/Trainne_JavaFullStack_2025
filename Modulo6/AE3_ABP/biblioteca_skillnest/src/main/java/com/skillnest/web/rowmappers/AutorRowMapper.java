package com.skillnest.web.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.skillnest.web.models.AutorDTO;

/**
 * RowMapper para mapear ResultSet a AutorDTO
 * Usado con JdbcTemplate
 */
public class AutorRowMapper implements RowMapper<AutorDTO> {
    
    @Override
    public AutorDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        AutorDTO autor = new AutorDTO();
        autor.setId(rs.getLong("id"));
        autor.setNombre(rs.getString("nombre"));
        autor.setApellido(rs.getString("apellido"));
        autor.setNacionalidad(rs.getString("nacionalidad"));
        
        // Manejo de fecha que puede ser null
        java.sql.Date fechaNacimiento = rs.getDate("fecha_nacimiento");
        if (fechaNacimiento != null) {
            autor.setFechaNacimiento(fechaNacimiento.toLocalDate());
        }
        
        autor.setBiografia(rs.getString("biografia"));
        
        return autor;
    }
}
