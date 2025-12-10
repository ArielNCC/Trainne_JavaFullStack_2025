package com.empresa.capacitaciones.service;

import com.empresa.capacitaciones.entity.Instructor;
import java.util.List;
import java.util.Optional;

public interface InstructorService {
    List<Instructor> findAll();
    Optional<Instructor> findById(Long id);
    Instructor save(Instructor instructor);
    void deleteById(Long id);
    Optional<Instructor> findByEmail(String email);
    List<Instructor> findByEspecialidad(String especialidad);
    List<Instructor> buscarPorNombre(String nombre);
}
