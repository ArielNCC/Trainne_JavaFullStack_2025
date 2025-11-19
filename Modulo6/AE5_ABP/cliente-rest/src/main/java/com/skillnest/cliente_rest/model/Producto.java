package com.skillnest.cliente_rest.model;

import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

// model/Producto.java
@Entity
@Table(name = "productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "nombre")
    private String nombre;
    
    @Column(name = "precio", precision = 10, scale = 2)
    private BigDecimal precio;
    
    @Column(name = "descripcion")
    private String descripcion;

    // Constructor vacío
    public Producto() {
    }

    public Producto(String nombre, BigDecimal precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    public Producto(String nombre, BigDecimal precio, String descripcion) {
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
    }

    // Constructor de compatibilidad con double
    public Producto(String nombre, double precio) {
        this.nombre = nombre;
        this.precio = BigDecimal.valueOf(precio);
    }

    public Producto(String nombre, double precio, String descripcion) {
        this.nombre = nombre;
        this.precio = BigDecimal.valueOf(precio);
        this.descripcion = descripcion;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    // Método de compatibilidad para double
    public void setPrecio(double precio) {
        this.precio = BigDecimal.valueOf(precio);
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    // Métodos de compatibilidad con ProductoDto (ocultos en JSON)
    @JsonIgnore
    public String getDetalle() {
        return this.descripcion;
    }

    @JsonIgnore
    public void setDetalle(String detalle) {
        this.descripcion = detalle;
    }

    @JsonIgnore
    public int getCantidad() {
        return 0; // Campo no existe en BD pero se usa en DTO
    }

    @JsonIgnore
    public void setCantidad(int cantidad) {
        // No hace nada porque el campo no existe en la BD
    }

    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}