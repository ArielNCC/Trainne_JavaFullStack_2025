package com.empresa.capacitaciones.repository;

import com.empresa.capacitaciones.entity.Inscripcion;
import com.empresa.capacitaciones.entity.Inscripcion.EstadoInscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {
    
    List<Inscripcion> findByEmpleadoId(Long empleadoId);
    
    List<Inscripcion> findByCursoId(Long cursoId);
    
    List<Inscripcion> findByEstado(EstadoInscripcion estado);
    
    @Query("SELECT i FROM Inscripcion i WHERE i.empleado.id = :empleadoId AND i.curso.id = :cursoId")
    Optional<Inscripcion> findByEmpleadoIdAndCursoId(Long empleadoId, Long cursoId);
    
    @Query("SELECT COUNT(i) FROM Inscripcion i WHERE i.curso.id = :cursoId")
    Long countInscripcionesByCursoId(Long cursoId);
}
