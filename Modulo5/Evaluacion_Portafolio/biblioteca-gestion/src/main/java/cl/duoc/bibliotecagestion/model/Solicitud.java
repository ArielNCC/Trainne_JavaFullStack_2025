package cl.duoc.bibliotecagestion.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Clase modelo que representa una Solicitud de Préstamo.
 * POJO (Plain Old Java Object) con los atributos de una solicitud.
 */
public class Solicitud implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer id;
    private String nombreUsuario;
    private String correoUsuario;
    private Integer libroId;
    private Timestamp fechaSolicitud;
    private String estado;
    
    // Para mostrar información del libro en las vistas
    private String tituloLibro;
    private String autorLibro;
    
    // Constructor vacío
    public Solicitud() {
    }
    
    // Constructor con parámetros básicos (sin ID para nuevos registros)
    public Solicitud(String nombreUsuario, String correoUsuario, Integer libroId) {
        this.nombreUsuario = nombreUsuario;
        this.correoUsuario = correoUsuario;
        this.libroId = libroId;
        this.estado = "PENDIENTE";
    }
    
    // Constructor completo
    public Solicitud(Integer id, String nombreUsuario, String correoUsuario, 
                    Integer libroId, Timestamp fechaSolicitud, String estado) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.correoUsuario = correoUsuario;
        this.libroId = libroId;
        this.fechaSolicitud = fechaSolicitud;
        this.estado = estado;
    }
    
    // Getters y Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getNombreUsuario() {
        return nombreUsuario;
    }
    
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
    
    public String getCorreoUsuario() {
        return correoUsuario;
    }
    
    public void setCorreoUsuario(String correoUsuario) {
        this.correoUsuario = correoUsuario;
    }
    
    public Integer getLibroId() {
        return libroId;
    }
    
    public void setLibroId(Integer libroId) {
        this.libroId = libroId;
    }
    
    public Timestamp getFechaSolicitud() {
        return fechaSolicitud;
    }
    
    public void setFechaSolicitud(Timestamp fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public String getTituloLibro() {
        return tituloLibro;
    }
    
    public void setTituloLibro(String tituloLibro) {
        this.tituloLibro = tituloLibro;
    }
    
    public String getAutorLibro() {
        return autorLibro;
    }
    
    public void setAutorLibro(String autorLibro) {
        this.autorLibro = autorLibro;
    }
    
    @Override
    public String toString() {
        return "Solicitud{" +
                "id=" + id +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", correoUsuario='" + correoUsuario + '\'' +
                ", libroId=" + libroId +
                ", fechaSolicitud=" + fechaSolicitud +
                ", estado='" + estado + '\'' +
                '}';
    }
}
