package com.empresa.capacitaciones.service;

import com.empresa.capacitaciones.entity.Empleado;
import java.util.List;
import java.util.Optional;

public interface EmpleadoService {
    List<Empleado> findAll();
    Optional<Empleado> findById(Long id);
    Empleado save(Empleado empleado);
    void deleteById(Long id);
    Optional<Empleado> findByEmail(String email);
    List<Empleado> findByDepartamento(String departamento);
    List<Empleado> buscarPorNombre(String nombre);
    Empleado findByUsername(String username);
}
