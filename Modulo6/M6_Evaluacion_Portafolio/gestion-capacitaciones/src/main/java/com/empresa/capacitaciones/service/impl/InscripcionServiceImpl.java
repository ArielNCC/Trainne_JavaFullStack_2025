package com.empresa.capacitaciones.service.impl;

import com.empresa.capacitaciones.entity.Curso;
import com.empresa.capacitaciones.entity.Empleado;
import com.empresa.capacitaciones.entity.Inscripcion;
import com.empresa.capacitaciones.entity.Inscripcion.EstadoInscripcion;
import com.empresa.capacitaciones.repository.CursoRepository;
import com.empresa.capacitaciones.repository.EmpleadoRepository;
import com.empresa.capacitaciones.repository.InscripcionRepository;
import com.empresa.capacitaciones.service.InscripcionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class InscripcionServiceImpl implements InscripcionService {
    
    @Autowired
    private InscripcionRepository inscripcionRepository;
    
    @Autowired
    private EmpleadoRepository empleadoRepository;
    
    @Autowired
    private CursoRepository cursoRepository;
    
    @Override
    public List<Inscripcion> findAll() {
        return inscripcionRepository.findAll();
    }
    
    @Override
    public Optional<Inscripcion> findById(Long id) {
        return inscripcionRepository.findById(id);
    }
    
    @Override
    public Inscripcion save(Inscripcion inscripcion) {
        return inscripcionRepository.save(inscripcion);
    }
    
    @Override
    public void deleteById(Long id) {
        inscripcionRepository.deleteById(id);
    }
    
    @Override
    public List<Inscripcion> findByEmpleadoId(Long empleadoId) {
        return inscripcionRepository.findByEmpleadoId(empleadoId);
    }
    
    @Override
    public List<Inscripcion> findByCursoId(Long cursoId) {
        return inscripcionRepository.findByCursoId(cursoId);
    }
    
    @Override
    public List<Inscripcion> findByEstado(EstadoInscripcion estado) {
        return inscripcionRepository.findByEstado(estado);
    }
    
    @Override
    public Optional<Inscripcion> findByEmpleadoIdAndCursoId(Long empleadoId, Long cursoId) {
        return inscripcionRepository.findByEmpleadoIdAndCursoId(empleadoId, cursoId);
    }
    
    @Override
    public boolean existsByEmpleadoAndCurso(Long empleadoId, Long cursoId) {
        return inscripcionRepository.findByEmpleadoIdAndCursoId(empleadoId, cursoId).isPresent();
    }
    
    @Override
    public Inscripcion inscribirEmpleado(Long empleadoId, Long cursoId) throws Exception {
        // Validar que el empleado existe
        Empleado empleado = empleadoRepository.findById(empleadoId)
            .orElseThrow(() -> new Exception("Empleado no encontrado"));
        
        // Validar que el curso existe
        Curso curso = cursoRepository.findById(cursoId)
            .orElseThrow(() -> new Exception("Curso no encontrado"));
        
        // Verificar que el empleado no esté ya inscrito en el curso
        Optional<Inscripcion> inscripcionExistente = findByEmpleadoIdAndCursoId(empleadoId, cursoId);
        if (inscripcionExistente.isPresent()) {
            throw new Exception("El empleado ya está inscrito en este curso");
        }
        
        // Verificar que haya cupos disponibles
        if (curso.getCuposDisponibles() <= 0) {
            throw new Exception("No hay cupos disponibles para este curso");
        }
        
        // Crear la inscripción
        Inscripcion inscripcion = new Inscripcion(empleado, curso);
        inscripcion.setEstado(EstadoInscripcion.INSCRITO);
        inscripcion.setFechaInscripcion(LocalDateTime.now());
        
        return inscripcionRepository.save(inscripcion);
    }
    
    @Override
    public void cancelarInscripcion(Long inscripcionId) throws Exception {
        Inscripcion inscripcion = inscripcionRepository.findById(inscripcionId)
            .orElseThrow(() -> new Exception("Inscripción no encontrada"));
        
        inscripcion.setEstado(EstadoInscripcion.CANCELADO);
        inscripcionRepository.save(inscripcion);
    }
    
    @Override
    public void confirmarInscripcion(Long inscripcionId) throws Exception {
        Inscripcion inscripcion = inscripcionRepository.findById(inscripcionId)
            .orElseThrow(() -> new Exception("Inscripción no encontrada"));
        
        inscripcion.setEstado(EstadoInscripcion.INSCRITO);
        inscripcionRepository.save(inscripcion);
    }
    
    @Override
    public void completarInscripcion(Long inscripcionId) throws Exception {
        Inscripcion inscripcion = inscripcionRepository.findById(inscripcionId)
            .orElseThrow(() -> new Exception("Inscripción no encontrada"));
        
        inscripcion.setEstado(EstadoInscripcion.COMPLETADO);
        inscripcionRepository.save(inscripcion);
    }
}
