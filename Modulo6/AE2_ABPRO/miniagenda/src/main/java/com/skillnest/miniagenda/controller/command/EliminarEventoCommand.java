package com.skillnest.miniagenda.controller.command;

import jakarta.validation.constraints.NotNull;

/**
 * Command Object para eliminar un evento
 */
public class EliminarEventoCommand {
    
    @NotNull(message = "El ID del evento es obligatorio")
    private Long id;
    
    // Constructores
    public EliminarEventoCommand() {
    }
    
    public EliminarEventoCommand(Long id) {
        this.id = id;
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
}
