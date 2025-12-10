package com.empresa.capacitaciones.service.impl;

import com.empresa.capacitaciones.entity.Empleado;
import com.empresa.capacitaciones.repository.EmpleadoRepository;
import com.empresa.capacitaciones.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EmpleadoServiceImpl implements EmpleadoService {
    
    @Autowired
    private EmpleadoRepository empleadoRepository;
    
    @Override
    public List<Empleado> findAll() {
        return empleadoRepository.findAll();
    }
    
    @Override
    public Optional<Empleado> findById(Long id) {
        return empleadoRepository.findById(id);
    }
    
    @Override
    public Empleado save(Empleado empleado) {
        return empleadoRepository.save(empleado);
    }
    
    @Override
    public void deleteById(Long id) {
        empleadoRepository.deleteById(id);
    }
    
    @Override
    public Optional<Empleado> findByEmail(String email) {
        return empleadoRepository.findByEmail(email);
    }
    
    @Override
    public List<Empleado> findByDepartamento(String departamento) {
        return empleadoRepository.findByDepartamento(departamento);
    }
    
    @Override
    public List<Empleado> buscarPorNombre(String nombre) {
        return empleadoRepository.findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(nombre, nombre);
    }
    
    @Override
    public Empleado findByUsername(String username) {
        // Por ahora retorna el primer empleado que coincida con el email/username
        // Idealmente deberías tener una relación con UserEntity
        Optional<Empleado> empleado = empleadoRepository.findByEmail(username);
        return empleado.orElse(null);
    }
}
