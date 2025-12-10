package com.empresa.capacitaciones.service;

import com.empresa.capacitaciones.entity.Curso;
import java.util.List;
import java.util.Optional;

public interface CursoService {
    List<Curso> findAll();
    Optional<Curso> findById(Long id);
    Curso save(Curso curso);
    void deleteById(Long id);
    List<Curso> findByNombre(String nombre);
    List<Curso> findByActivoTrue();
    List<Curso> findCursosDisponibles();
    List<Curso> findCursosConCuposDisponibles();
    List<Curso> findByInstructorId(Long instructorId);
    boolean tieneCuposDisponibles(Long cursoId);
}
