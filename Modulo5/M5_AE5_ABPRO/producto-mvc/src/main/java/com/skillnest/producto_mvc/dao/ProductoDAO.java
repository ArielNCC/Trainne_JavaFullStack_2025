package com.skillnest.producto_mvc.dao;

import com.skillnest.producto_mvc.model.Producto;
import java.util.List;

public interface ProductoDAO {
    Producto findById(Integer id);
    List<Producto> findAll();
    List<Producto> findByCategoria(String categoria);
    List<Producto> findActivos();
    void save(Producto producto);
    void update(Producto producto);
    void delete(Integer id);
}
