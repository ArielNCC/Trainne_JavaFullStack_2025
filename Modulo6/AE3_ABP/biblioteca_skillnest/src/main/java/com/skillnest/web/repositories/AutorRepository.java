package com.skillnest.web.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.skillnest.web.models.Autor;

/**
 * Repository JPA para Autor
 * Hereda de JpaRepository para operaciones CRUD automáticas
 */
@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {
    
    // Método derivado: busca autores por apellido
    List<Autor> findByApellidoContainingIgnoreCase(String apellido);
    
    // Método derivado: busca autores por nombre
    List<Autor> findByNombreContainingIgnoreCase(String nombre);
    
    // Método derivado: busca autores por nacionalidad
    List<Autor> findByNacionalidad(String nacionalidad);
    
    // Consulta personalizada con JPQL
    @Query("SELECT a FROM Autor a WHERE CONCAT(a.nombre, ' ', a.apellido) LIKE %:nombreCompleto%")
    List<Autor> buscarPorNombreCompleto(@Param("nombreCompleto") String nombreCompleto);
    
    // Consulta para obtener autores con libros
    @Query("SELECT DISTINCT a FROM Autor a LEFT JOIN FETCH a.libros")
    List<Autor> findAllWithLibros();
}
