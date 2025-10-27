package com.skillnest.miniagenda.controller.command;

import java.time.LocalDate;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Command Object para modificar un evento existente
 */
public class ModificarEventoCommand {
    
    @NotNull(message = "El ID del evento es obligatorio")
    private Long id;
    
    @NotBlank(message = "El título es obligatorio")
    @Size(min = 3, max = 100, message = "El título debe tener entre 3 y 100 caracteres")
    private String titulo;
    
    @NotNull(message = "La fecha es obligatoria")
    @FutureOrPresent(message = "La fecha debe ser hoy o en el futuro")
    private LocalDate fecha;
    
    @NotBlank(message = "La descripción es obligatoria")
    @Size(min = 10, max = 500, message = "La descripción debe tener entre 10 y 500 caracteres")
    private String descripcion;
    
    @NotBlank(message = "El responsable es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre del responsable debe tener entre 3 y 100 caracteres")
    private String responsable;
    
    // Constructores
    public ModificarEventoCommand() {
    }
    
    public ModificarEventoCommand(Long id, String titulo, LocalDate fecha, String descripcion, String responsable) {
        this.id = id;
        this.titulo = titulo;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.responsable = responsable;
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTitulo() {
        return titulo;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public LocalDate getFecha() {
        return fecha;
    }
    
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getResponsable() {
        return responsable;
    }
    
    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }
}
