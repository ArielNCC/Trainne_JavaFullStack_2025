package com.skillnest.producto_mvc.service;

import com.skillnest.producto_mvc.model.Producto;
import java.util.List;

public interface ProductoService {
    Producto buscarPorId(Integer id);
    List<Producto> listarTodos();
    List<Producto> listarActivos();
    List<Producto> listarPorCategoria(String categoria);
    void guardar(Producto producto);
    void actualizar(Producto producto);
    void eliminar(Integer id);
}
