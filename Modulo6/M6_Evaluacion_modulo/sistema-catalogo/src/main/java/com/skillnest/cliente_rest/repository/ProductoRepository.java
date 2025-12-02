package com.skillnest.cliente_rest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.skillnest.cliente_rest.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    
    // Búsqueda por nombre (case insensitive)
    List<Producto> findByNombreContainingIgnoreCase(String nombre);
    
    // Productos con stock mayor a un valor específico
    List<Producto> findByStockDisponibleGreaterThan(Integer stock);
    
    // Productos con stock entre rangos
    List<Producto> findByStockDisponibleBetween(Integer min, Integer max);
    
    // Productos por rango de precio
    @Query("SELECT p FROM Producto p WHERE p.precio BETWEEN :minPrecio AND :maxPrecio")
    List<Producto> findByPrecioBetween(@Param("minPrecio") Double minPrecio, @Param("maxPrecio") Double maxPrecio);
    
    // Productos ordenados por precio
    List<Producto> findAllByOrderByPrecioAsc();
    List<Producto> findAllByOrderByPrecioDesc();
    
    // Productos ordenados por stock
    List<Producto> findAllByOrderByStockDisponibleDesc();
}
