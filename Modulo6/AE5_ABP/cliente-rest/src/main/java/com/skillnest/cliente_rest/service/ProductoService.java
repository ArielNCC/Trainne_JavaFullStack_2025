package com.skillnest.cliente_rest.service;

import java.util.List;

import com.skillnest.cliente_rest.model.Producto;
import com.skillnest.cliente_rest.model.ProductoDto;

//aplicar logica de negocio
public interface ProductoService {

    public String obtenerMensaje();
    public String formatearProducto(String nombre, double precio);
    //metodos CRUD
    public Producto guardaProducto(ProductoDto productoDto);
	public List<Producto> listarTodos();
	public Producto obtener(Long id);
	public Producto crear(Producto producto);
	public Producto eliminar(long id);
	public Producto actualizar(long id, Producto producto);
    
}
