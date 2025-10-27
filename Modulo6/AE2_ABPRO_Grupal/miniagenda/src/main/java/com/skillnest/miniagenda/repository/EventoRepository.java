package com.skillnest.miniagenda.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.skillnest.miniagenda.model.Evento;

/**
 * Repositorio para la entidad Evento
 * 
 * @Repository: Marca esta interfaz como un repositorio de datos.
 * Extiende JpaRepository que proporciona métodos CRUD básicos y más funcionalidades.
 * Spring Data JPA implementará automáticamente esta interfaz.
 */
@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {
    
    /**
     * Encuentra eventos por fecha específica
     * Spring Data JPA genera automáticamente la implementación basada en el nombre del método
     */
    List<Evento> findByFecha(LocalDate fecha);
    
    /**
     * Encuentra eventos por responsable
     */
    List<Evento> findByResponsable(String responsable);
    
    /**
     * Encuentra eventos que contengan un término en el título (ignorando mayúsculas/minúsculas)
     */
    List<Evento> findByTituloContainingIgnoreCase(String titulo);
    
    /**
     * Encuentra eventos entre dos fechas
     */
    List<Evento> findByFechaBetween(LocalDate fechaInicio, LocalDate fechaFin);
    
    /**
     * Obtiene todos los eventos ordenados por fecha ascendente
     */
    List<Evento> findAllByOrderByFechaAsc();
    
    /**
     * Consulta personalizada para buscar eventos por mes y año
     */
    @Query("SELECT e FROM Evento e WHERE YEAR(e.fecha) = :year AND MONTH(e.fecha) = :month ORDER BY e.fecha")
    List<Evento> findEventosByMonthAndYear(@Param("year") int year, @Param("month") int month);
}