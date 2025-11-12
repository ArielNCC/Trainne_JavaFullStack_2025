package com.skillnest.formulario_demo.dao;

import com.skillnest.formulario_demo.model.Producto;
import java.util.List;

public interface ProductoDAO {
    
    // Crear un nuevo producto
    boolean crear(Producto producto);
    
    // Obtener todos los productos
    List<Producto> obtenerTodos();
    
    // Eliminar un producto por ID
    boolean eliminar(int id);
    
    // Método adicional: Obtener un producto por ID (opcional pero útil)
    Producto obtenerPorId(int id);
}
