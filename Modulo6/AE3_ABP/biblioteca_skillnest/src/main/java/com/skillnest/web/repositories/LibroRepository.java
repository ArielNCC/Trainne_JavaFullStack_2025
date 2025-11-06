package com.skillnest.web.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.skillnest.web.models.Libro;

/**
 * Repository JPA para Libro
 * Hereda de JpaRepository para operaciones CRUD automáticas
 */
@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
    
    // Método derivado: busca libro por ISBN
    Libro findByIsbn(String isbn);
    
    // Método derivado: busca libros por título
    List<Libro> findByTituloContainingIgnoreCase(String titulo);
    
    // Método derivado: busca libros por género
    List<Libro> findByGenero(String genero);
    
    // Método derivado: busca libros por autor
    List<Libro> findByAutorId(Long autorId);
    
    // Método derivado: busca libros disponibles
    List<Libro> findByCantidadDisponibleGreaterThan(Integer cantidad);
    
    // Consulta personalizada con JPQL para libros con autor
    @Query("SELECT l FROM Libro l JOIN FETCH l.autor WHERE l.id = :id")
    Libro findByIdWithAutor(@Param("id") Long id);
    
    // Consulta para obtener todos los libros con sus autores
    @Query("SELECT DISTINCT l FROM Libro l LEFT JOIN FETCH l.autor ORDER BY l.titulo")
    List<Libro> findAllWithAutor();
    
    // Consulta para buscar libros disponibles
    @Query("SELECT l FROM Libro l WHERE l.cantidadDisponible > 0 ORDER BY l.titulo")
    List<Libro> findLibrosDisponibles();
    
    // Consulta por rango de años
    @Query("SELECT l FROM Libro l WHERE l.anioPublicacion BETWEEN :inicio AND :fin ORDER BY l.anioPublicacion")
    List<Libro> findByAnioPublicacionBetween(@Param("inicio") Integer inicio, @Param("fin") Integer fin);
}
