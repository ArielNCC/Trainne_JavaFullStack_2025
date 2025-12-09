package cl.duoc.bibliotecagestion.model;

import java.io.Serializable;

/**
 * Clase modelo que representa un Libro.
 * POJO (Plain Old Java Object) con los atributos de un libro.
 */
public class Libro implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer id;
    private String titulo;
    private String autor;
    private String isbn;
    private Boolean disponible;
    
    // Constructor vacío
    public Libro() {
    }
    
    // Constructor con parámetros (sin ID para nuevos registros)
    public Libro(String titulo, String autor, String isbn, Boolean disponible) {
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.disponible = disponible;
    }
    
    // Constructor completo
    public Libro(Integer id, String titulo, String autor, String isbn, Boolean disponible) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.disponible = disponible;
    }
    
    // Getters y Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getTitulo() {
        return titulo;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public String getAutor() {
        return autor;
    }
    
    public void setAutor(String autor) {
        this.autor = autor;
    }
    
    public String getIsbn() {
        return isbn;
    }
    
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    
    public Boolean getDisponible() {
        return disponible;
    }
    
    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }
    
    @Override
    public String toString() {
        return "Libro{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", isbn='" + isbn + '\'' +
                ", disponible=" + disponible +
                '}';
    }
}
