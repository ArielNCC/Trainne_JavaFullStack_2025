package com.skillnest.producto_mvc.service.impl;

import com.skillnest.producto_mvc.dao.ProductoDAO;
import com.skillnest.producto_mvc.model.Producto;
import com.skillnest.producto_mvc.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService {
    
    @Autowired
    private ProductoDAO productoDAO;
    
    @Override
    public Producto buscarPorId(Integer id) {
        return productoDAO.findById(id);
    }
    
    @Override
    public List<Producto> listarTodos() {
        return productoDAO.findAll();
    }
    
    @Override
    public List<Producto> listarActivos() {
        return productoDAO.findActivos();
    }
    
    @Override
    public List<Producto> listarPorCategoria(String categoria) {
        return productoDAO.findByCategoria(categoria);
    }
    
    @Override
    public void guardar(Producto producto) {
        productoDAO.save(producto);
    }
    
    @Override
    public void actualizar(Producto producto) {
        productoDAO.update(producto);
    }
    
    @Override
    public void eliminar(Integer id) {
        productoDAO.delete(id);
    }
}
