package com.skillnest.web.models;

/**
 * DTO (Data Transfer Object) para Libro
 * Usado con JdbcTemplate para operaciones JDBC
 */
public class LibroDTO {
    private Long id;
    private String titulo;
    private String isbn;
    private Integer anioPublicacion;
    private String genero;
    private String editorial;
    private Integer numeroPaginas;
    private Integer cantidadDisponible;
    private Integer cantidadTotal;
    private Long autorId;
    private String autorNombre; // Para mostrar en vistas

    // Constructores
    public LibroDTO() {
        super();
    }

    public LibroDTO(String titulo, String isbn, Long autorId) {
        this.titulo = titulo;
        this.isbn = isbn;
        this.autorId = autorId;
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

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getAnioPublicacion() {
        return anioPublicacion;
    }

    public void setAnioPublicacion(Integer anioPublicacion) {
        this.anioPublicacion = anioPublicacion;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public Integer getNumeroPaginas() {
        return numeroPaginas;
    }

    public void setNumeroPaginas(Integer numeroPaginas) {
        this.numeroPaginas = numeroPaginas;
    }

    public Integer getCantidadDisponible() {
        return cantidadDisponible;
    }

    public void setCantidadDisponible(Integer cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }

    public Integer getCantidadTotal() {
        return cantidadTotal;
    }

    public void setCantidadTotal(Integer cantidadTotal) {
        this.cantidadTotal = cantidadTotal;
    }

    public Long getAutorId() {
        return autorId;
    }

    public void setAutorId(Long autorId) {
        this.autorId = autorId;
    }

    public String getAutorNombre() {
        return autorNombre;
    }

    public void setAutorNombre(String autorNombre) {
        this.autorNombre = autorNombre;
    }

    @Override
    public String toString() {
        return "LibroDTO{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", isbn='" + isbn + '\'' +
                ", autorNombre='" + autorNombre + '\'' +
                '}';
    }
}
