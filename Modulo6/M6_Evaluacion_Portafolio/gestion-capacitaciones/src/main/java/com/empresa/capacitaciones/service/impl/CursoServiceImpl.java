package com.empresa.capacitaciones.service.impl;

import com.empresa.capacitaciones.entity.Curso;
import com.empresa.capacitaciones.repository.CursoRepository;
import com.empresa.capacitaciones.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CursoServiceImpl implements CursoService {
    
    @Autowired
    private CursoRepository cursoRepository;
    
    @Override
    public List<Curso> findAll() {
        return cursoRepository.findAll();
    }
    
    @Override
    public Optional<Curso> findById(Long id) {
        return cursoRepository.findById(id);
    }
    
    @Override
    public Curso save(Curso curso) {
        return cursoRepository.save(curso);
    }
    
    @Override
    public void deleteById(Long id) {
        cursoRepository.deleteById(id);
    }
    
    @Override
    public List<Curso> findByNombre(String nombre) {
        return cursoRepository.findByNombreContainingIgnoreCase(nombre);
    }
    
    @Override
    public List<Curso> findCursosDisponibles() {
        return cursoRepository.findCursosDisponibles(LocalDate.now());
    }
    
    @Override
    public List<Curso> findCursosConCuposDisponibles() {
        return cursoRepository.findCursosConCuposDisponibles(LocalDate.now());
    }
    
    @Override
    public List<Curso> findByInstructorId(Long instructorId) {
        return cursoRepository.findByInstructorId(instructorId);
    }
    
    @Override
    public boolean tieneCuposDisponibles(Long cursoId) {
        Optional<Curso> curso = cursoRepository.findById(cursoId);
        return curso.map(c -> c.getCuposDisponibles() > 0).orElse(false);
    }
    
    @Override
    public List<Curso> findByActivoTrue() {
        return cursoRepository.findByActivoTrue();
    }
}
