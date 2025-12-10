package com.empresa.capacitaciones.repository;

import com.empresa.capacitaciones.entity.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
    
    Optional<Empleado> findByEmail(String email);
    
    List<Empleado> findByDepartamento(String departamento);
    
    List<Empleado> findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(String nombre, String apellido);
}
