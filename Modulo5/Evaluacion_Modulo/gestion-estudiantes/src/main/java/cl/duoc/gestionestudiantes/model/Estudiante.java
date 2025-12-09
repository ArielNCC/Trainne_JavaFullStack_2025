package cl.duoc.gestionestudiantes.model;

import java.io.Serializable;

/**
 * Clase modelo que representa un Estudiante.
 * POJO (Plain Old Java Object) con los atributos de un estudiante.
 */
public class Estudiante implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer id;
    private String nombreCompleto;
    private String correoElectronico;
    private String carrera;
    
    // Constructor vacío
    public Estudiante() {
    }
    
    // Constructor con parámetros (sin ID para nuevos registros)
    public Estudiante(String nombreCompleto, String correoElectronico, String carrera) {
        this.nombreCompleto = nombreCompleto;
        this.correoElectronico = correoElectronico;
        this.carrera = carrera;
    }
    
    // Constructor completo
    public Estudiante(Integer id, String nombreCompleto, String correoElectronico, String carrera) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.correoElectronico = correoElectronico;
        this.carrera = carrera;
    }
    
    // Getters y Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getNombreCompleto() {
        return nombreCompleto;
    }
    
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }
    
    public String getCorreoElectronico() {
        return correoElectronico;
    }
    
    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }
    
    public String getCarrera() {
        return carrera;
    }
    
    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }
    
    @Override
    public String toString() {
        return "Estudiante{" +
                "id=" + id +
                ", nombreCompleto='" + nombreCompleto + '\'' +
                ", correoElectronico='" + correoElectronico + '\'' +
                ", carrera='" + carrera + '\'' +
                '}';
    }
}
