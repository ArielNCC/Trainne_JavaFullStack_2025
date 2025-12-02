package com.skillnest.cliente_rest.restcontrollers;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.skillnest.cliente_rest.model.Producto;
import com.skillnest.cliente_rest.service.ProductoService;

/**
 * Controlador REST del Sistema Catálogo
 * Implementa todas las operaciones CRUD con versionado /api/v1
 * Complementa la interfaz web JSP con endpoints REST para integración
 */
@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProductoRestController {

    @Autowired
    private ProductoService productoService;
	
    /**
     * GET /api/v1/productos - Obtiene todos los productos
     * Soporta paginación y ordenamiento
     */
    @GetMapping
    public ResponseEntity<?> listarProductos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String direction) {
        
        try {
            Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") ? 
                Sort.Direction.DESC : Sort.Direction.ASC;
            
            Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sort));
            Page<Producto> productos = productoService.listarTodos(pageable);
            
            Map<String, Object> response = Map.of(
                "productos", productos.getContent(),
                "currentPage", productos.getNumber(),
                "totalItems", productos.getTotalElements(),
                "totalPages", productos.getTotalPages(),
                "size", productos.getSize()
            );
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Error al obtener productos", "mensaje", e.getMessage()));
        }
    }
    
    /**
     * GET /api/productos/all - Obtiene todos los productos sin paginación
     */
    @GetMapping("/all")
    public ResponseEntity<List<Producto>> listarTodosLosProductos() {
        try {
            List<Producto> productos = productoService.listarTodos();
            return ResponseEntity.ok(productos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * GET /api/productos/listar - Obtiene todos los productos (alias de /all)
     */
    @GetMapping("/listar")
    public ResponseEntity<List<Producto>> listarProductos() {
        return listarTodosLosProductos();
    }
	
    /**
     * GET /api/v1/productos/{id} - Obtiene un producto por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerProducto(@PathVariable Long id) {
        try {
            Optional<Producto> producto = productoService.obtener(id);
            if (producto.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Producto no encontrado", "id", id));
            }
            return ResponseEntity.ok(producto.get());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Error al obtener producto", "mensaje", e.getMessage()));
        }
    }

    /**
     * POST /api/v1/productos - Crea un nuevo producto
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> crearProducto(@RequestBody Producto producto) {
        try {
            Producto creado = productoService.crear(producto);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "mensaje", "Producto creado exitosamente",
                "producto", creado
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", "Datos inválidos", "mensaje", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Error al crear producto", "mensaje", e.getMessage()));
        }
    }
    
    /**
     * PUT /api/v1/productos/{id} - Actualiza completamente un producto
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> actualizarProducto(@PathVariable Long id, @RequestBody Producto producto) {
        try {
            Producto actualizado = productoService.actualizar(id, producto);
            return ResponseEntity.ok(Map.of(
                "mensaje", "Producto actualizado exitosamente",
                "producto", actualizado
            ));
        } catch (Exception e) {
            // Verificar el tipo de excepción para respuesta apropiada
            if (e instanceof IllegalArgumentException) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Datos inválidos", "mensaje", e.getMessage()));
            } else if (e.getMessage() != null && e.getMessage().contains("no encontrado")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Producto no encontrado", "id", id));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al actualizar producto", "mensaje", e.getMessage()));
            }
        }
    }
    
    /**
     * PATCH /api/v1/productos/{id}/stock - Actualiza solo el stock de un producto
     */
    @PatchMapping("/{id}/stock")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> actualizarStock(@PathVariable Long id, @RequestParam Integer stock) {
        try {
            Producto actualizado = productoService.actualizarStock(id, stock);
            return ResponseEntity.ok(Map.of(
                "mensaje", "Stock actualizado exitosamente",
                "producto", actualizado
            ));
        } catch (Exception e) {
            // Verificar el tipo de excepción para respuesta apropiada
            if (e instanceof IllegalArgumentException) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Stock inválido", "mensaje", e.getMessage()));
            } else if (e.getMessage() != null && e.getMessage().contains("no encontrado")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Producto no encontrado", "id", id));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al actualizar stock", "mensaje", e.getMessage()));
            }
        }
    }
    
    /**
     * DELETE /api/v1/productos/{id} - Elimina un producto
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> eliminarProducto(@PathVariable Long id) {
        try {
            productoService.eliminar(id);
            return ResponseEntity.ok(Map.of(
                "mensaje", "Producto eliminado exitosamente",
                "id", id
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", "Producto no encontrado", "id", id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Error al eliminar producto", "mensaje", e.getMessage()));
        }
    }
    
    /**
     * GET /api/v1/productos/buscar - Busca productos por nombre
     */
    @GetMapping("/buscar")
    public ResponseEntity<?> buscarProductos(@RequestParam String nombre) {
        try {
            List<Producto> productos = productoService.buscarPorNombre(nombre);
            return ResponseEntity.ok(Map.of(
                "productos", productos,
                "total", productos.size(),
                "termino", nombre
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Error al buscar productos", "mensaje", e.getMessage()));
        }
    }
    
    /**
     * GET /api/v1/productos/con-stock - Obtiene productos con stock disponible
     */
    @GetMapping("/con-stock")
    public ResponseEntity<?> productosConStock() {
        try {
            List<Producto> productos = productoService.obtenerProductosConStock();
            return ResponseEntity.ok(Map.of(
                "productos", productos,
                "total", productos.size()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Error al obtener productos con stock", "mensaje", e.getMessage()));
        }
    }
}
