package com.empresa.capacitaciones.service.impl;

import com.empresa.capacitaciones.entity.Instructor;
import com.empresa.capacitaciones.repository.InstructorRepository;
import com.empresa.capacitaciones.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class InstructorServiceImpl implements InstructorService {
    
    @Autowired
    private InstructorRepository instructorRepository;
    
    @Override
    public List<Instructor> findAll() {
        return instructorRepository.findAll();
    }
    
    @Override
    public Optional<Instructor> findById(Long id) {
        return instructorRepository.findById(id);
    }
    
    @Override
    public Instructor save(Instructor instructor) {
        return instructorRepository.save(instructor);
    }
    
    @Override
    public void deleteById(Long id) {
        instructorRepository.deleteById(id);
    }
    
    @Override
    public Optional<Instructor> findByEmail(String email) {
        return instructorRepository.findByEmail(email);
    }
    
    @Override
    public List<Instructor> findByEspecialidad(String especialidad) {
        return instructorRepository.findByEspecialidad(especialidad);
    }
    
    @Override
    public List<Instructor> buscarPorNombre(String nombre) {
        return instructorRepository.findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(nombre, nombre);
    }
}
