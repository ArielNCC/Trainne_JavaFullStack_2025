package com.skillnest.agendacontactos.model;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

/**
 * Modelo Contacto como Bean de Spring y Entidad JPA
 * 
 * Anotaciones utilizadas:
 * - @Component: Permite que la clase sea detectada automáticamente por Spring y gestionada como un Bean.
 * - @Entity: Marca la clase como una entidad JPA para persistencia en base de datos.
 * - @Table: Especifica el nombre de la tabla en la base de datos.
 * - @Id: Marca el campo como clave primaria.
 * - @GeneratedValue: Especifica que el valor se genera automáticamente.
 * - @Column: Configura las propiedades de la columna en la base de datos.
 */
@Component
@Entity
@Table(name = "contactos")
public class Contacto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;
    
    @Column(name = "correo", nullable = false, length = 100, unique = true)
    private String correo;
    
    @Column(name = "telefono", nullable = false, length = 20)
    private String telefono;
    
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;
    
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    public Contacto() {}

    public Contacto(String nombre, String correo, String telefono) {
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
    }

    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        fechaActualizacion = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        fechaActualizacion = LocalDateTime.now();
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    
    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }
    public void setFechaActualizacion(LocalDateTime fechaActualizacion) { this.fechaActualizacion = fechaActualizacion; }
}
