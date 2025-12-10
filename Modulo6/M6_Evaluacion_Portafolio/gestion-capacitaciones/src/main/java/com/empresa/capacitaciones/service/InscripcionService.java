package com.empresa.capacitaciones.service;

import com.empresa.capacitaciones.entity.Inscripcion;
import com.empresa.capacitaciones.entity.Inscripcion.EstadoInscripcion;
import java.util.List;
import java.util.Optional;

public interface InscripcionService {
    List<Inscripcion> findAll();
    Optional<Inscripcion> findById(Long id);
    Inscripcion save(Inscripcion inscripcion);
    void deleteById(Long id);
    List<Inscripcion> findByEmpleadoId(Long empleadoId);
    List<Inscripcion> findByCursoId(Long cursoId);
    List<Inscripcion> findByEstado(EstadoInscripcion estado);
    Optional<Inscripcion> findByEmpleadoIdAndCursoId(Long empleadoId, Long cursoId);
    boolean existsByEmpleadoAndCurso(Long empleadoId, Long cursoId);
    Inscripcion inscribirEmpleado(Long empleadoId, Long cursoId) throws Exception;
    void cancelarInscripcion(Long inscripcionId) throws Exception;
    void confirmarInscripcion(Long inscripcionId) throws Exception;
    void completarInscripcion(Long inscripcionId) throws Exception;
}
