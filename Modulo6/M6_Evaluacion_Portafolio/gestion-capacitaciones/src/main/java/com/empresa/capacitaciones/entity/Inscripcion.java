package com.empresa.capacitaciones.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "inscripciones", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"empleado_id", "curso_id"})
})
public class Inscripcion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empleado_id", nullable = false)
    private Empleado empleado;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id", nullable = false)
    private Curso curso;
    
    @Column(nullable = false)
    private LocalDateTime fechaInscripcion;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoInscripcion estado;
    
    @Column(columnDefinition = "TEXT")
    private String observaciones;
    
    public enum EstadoInscripcion {
        INSCRITO,
        EN_CURSO,
        COMPLETADO,
        CANCELADO
    }
    
    // Constructors
    public Inscripcion() {
        this.fechaInscripcion = LocalDateTime.now();
        this.estado = EstadoInscripcion.INSCRITO;
    }
    
    public Inscripcion(Empleado empleado, Curso curso) {
        this();
        this.empleado = empleado;
        this.curso = curso;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Empleado getEmpleado() {
        return empleado;
    }
    
    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }
    
    public Curso getCurso() {
        return curso;
    }
    
    public void setCurso(Curso curso) {
        this.curso = curso;
    }
    
    public LocalDateTime getFechaInscripcion() {
        return fechaInscripcion;
    }
    
    public void setFechaInscripcion(LocalDateTime fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }
    
    public EstadoInscripcion getEstado() {
        return estado;
    }
    
    public void setEstado(EstadoInscripcion estado) {
        this.estado = estado;
    }
    
    public String getObservaciones() {
        return observaciones;
    }
    
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
