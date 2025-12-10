package com.empresa.capacitaciones.repository;

import com.empresa.capacitaciones.entity.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {
    
    List<Curso> findByActivoTrue();
    
    List<Curso> findByNombreContainingIgnoreCase(String nombre);
    
    @Query("SELECT c FROM Curso c WHERE c.fechaInicio >= :fechaActual AND c.activo = true")
    List<Curso> findCursosDisponibles(LocalDate fechaActual);
    
    List<Curso> findByInstructorId(Long instructorId);
    
    @Query("SELECT c FROM Curso c WHERE SIZE(c.inscripciones) < c.cupoMaximo AND c.fechaInicio >= :fechaActual AND c.activo = true")
    List<Curso> findCursosConCuposDisponibles(LocalDate fechaActual);
}
