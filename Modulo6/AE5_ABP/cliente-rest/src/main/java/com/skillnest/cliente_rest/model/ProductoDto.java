package com.skillnest.cliente_rest.model;

import java.math.BigDecimal;

public class ProductoDto {
    private String nombre;
    private String detalle;
    private int cantidad;
    private BigDecimal precio;

    public ProductoDto() {
    }

    public ProductoDto(String nombre, String detalle, int cantidad, BigDecimal precio) {
        this.nombre = nombre;
        this.detalle = detalle;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    // Constructor de compatibilidad con double
    public ProductoDto(String nombre, String detalle, int cantidad, double precio) {
        this.nombre = nombre;
        this.detalle = detalle;
        this.cantidad = cantidad;
        this.precio = BigDecimal.valueOf(precio);
    }

    // Getters y setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    // Método de compatibilidad
    public void setPrecio(double precio) {
        this.precio = BigDecimal.valueOf(precio);
    }

    public double getPrecioAsDouble() {
        return precio != null ? precio.doubleValue() : 0.0;
    }
}
