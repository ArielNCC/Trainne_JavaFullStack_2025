package com.skillnest.cliente_rest.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skillnest.cliente_rest.model.Producto;
import com.skillnest.cliente_rest.model.ProductoDto;
import com.skillnest.cliente_rest.repository.ProductoRepository;

@Service
@Transactional
public class ProductoServiceImpl implements ProductoService {
	
	@Autowired
	private ProductoRepository productoRepository;
	
	// Métodos legacy mantenidos para compatibilidad
	@Override
    public String obtenerMensaje() {
        return "¡Servicio de catálogo de productos funcionando!";
    }
	
    @Override
    public String formatearProducto(String nombre, double precio) {
        return "Producto: " + nombre + ", Precio: $" + precio;
    }

	@Override
	public Producto guardaProducto(ProductoDto productoDto) {
		Producto producto = new Producto();
		producto.setNombre(productoDto.getNombre());
		producto.setPrecio(productoDto.getPrecio());
		producto.setDescripcion(productoDto.getDetalle());
		producto.setStockDisponible(productoDto.getCantidad());
		
		validarProducto(producto);
		return productoRepository.save(producto);
	}

	// Métodos CRUD principales para la API REST
	@Override
	@Transactional(readOnly = true)
	public List<Producto> listarTodos() {
		return productoRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Producto> listarTodos(Pageable pageable) {
		return productoRepository.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Producto> obtener(Long id) {
		return productoRepository.findById(id);
	}

	@Override
	public Producto crear(Producto producto) {
		validarProducto(producto);
		return productoRepository.save(producto);
	}

	@Override
	public Producto actualizar(Long id, Producto producto) {
		Optional<Producto> existenteOpt = productoRepository.findById(id);
		
		if (existenteOpt.isEmpty()) {
			throw new RuntimeException("Producto no encontrado con ID: " + id);
		}
		
		validarProducto(producto);
		producto.setId(id);
		return productoRepository.save(producto);
	}

	@Override
	public void eliminar(Long id) {
		if (!productoRepository.existsById(id)) {
			throw new RuntimeException("Producto no encontrado con ID: " + id);
		}
		productoRepository.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean existeProducto(Long id) {
		return productoRepository.existsById(id);
	}

	// Métodos adicionales para funcionalidad del catálogo
	@Override
	@Transactional(readOnly = true)
	public List<Producto> buscarPorNombre(String nombre) {
		return productoRepository.findByNombreContainingIgnoreCase(nombre);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Producto> obtenerProductosConStock() {
		return productoRepository.findByStockDisponibleGreaterThan(0);
	}

	@Override
	public Producto actualizarStock(Long id, Integer nuevoStock) {
		Optional<Producto> productoOpt = productoRepository.findById(id);
		
		if (productoOpt.isEmpty()) {
			throw new RuntimeException("Producto no encontrado con ID: " + id);
		}
		
		if (nuevoStock < 0) {
			throw new IllegalArgumentException("El stock no puede ser negativo");
		}
		
		Producto producto = productoOpt.get();
		producto.setStockDisponible(nuevoStock);
		return productoRepository.save(producto);
	}

	// Validación de productos
	@Override
	public void validarProducto(Producto producto) {
		if (producto == null) {
			throw new IllegalArgumentException("El producto no puede ser null");
		}
		
		if (producto.getNombre() == null || producto.getNombre().trim().isEmpty()) {
			throw new IllegalArgumentException("El nombre del producto es obligatorio");
		}
		
		if (producto.getPrecio() == null || producto.getPrecio().doubleValue() <= 0) {
			throw new IllegalArgumentException("El precio debe ser mayor a cero");
		}
		
		if (producto.getStockDisponible() != null && producto.getStockDisponible() < 0) {
			throw new IllegalArgumentException("El stock disponible no puede ser negativo");
		}
		
		// Si no se proporciona stock, establecer en 0
		if (producto.getStockDisponible() == null) {
			producto.setStockDisponible(0);
		}
	}
}

