package com.skillnest.cliente_rest.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.skillnest.cliente_rest.model.Producto;
import com.skillnest.cliente_rest.model.ProductoDto;

/**
 * Servicio para la gestión de productos con operaciones CRUD completas
 * y lógica de negocio específica para el catálogo de productos
 */
public interface ProductoService {

    // Métodos legacy mantenidos para compatibilidad
    String obtenerMensaje();
    String formatearProducto(String nombre, double precio);
    Producto guardaProducto(ProductoDto productoDto);
    
    // Métodos CRUD principales para la API REST
    List<Producto> listarTodos();
    Page<Producto> listarTodos(Pageable pageable);
    Optional<Producto> obtener(Long id);
    Producto crear(Producto producto);
    Producto actualizar(Long id, Producto producto);
    void eliminar(Long id);
    boolean existeProducto(Long id);
    
    // Métodos adicionales para funcionalidad del catálogo
    List<Producto> buscarPorNombre(String nombre);
    List<Producto> obtenerProductosConStock();
    Producto actualizarStock(Long id, Integer nuevoStock);
    
    // Validación de productos
    void validarProducto(Producto producto);
}
