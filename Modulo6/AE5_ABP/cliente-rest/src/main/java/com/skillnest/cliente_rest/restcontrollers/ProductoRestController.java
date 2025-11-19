package com.skillnest.cliente_rest.restcontrollers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skillnest.cliente_rest.model.Producto;
import com.skillnest.cliente_rest.service.ProductoService;

@RestController
@RequestMapping("/api/productos")
public class ProductoRestController {

    @Autowired
    private ProductoService productoService;
	
	@GetMapping("/listar")
    public List<Producto> listar() {
        return productoService.listarTodos();
    }
	
    @GetMapping("/{id}")
    public ResponseEntity<?> obtener(@PathVariable long id) {
        try {
            Producto producto = productoService.obtener(id);
            if (producto == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Producto no encontrado", "id", id));
            }
            return ResponseEntity.ok(producto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", "Producto no encontrado", "id", id));
        }
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Producto producto) {
        try {
            Producto creado = productoService.crear(producto);
            return ResponseEntity.status(HttpStatus.CREATED).body(creado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", "Error al crear producto", "mensaje", e.getMessage()));
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable long id, @RequestBody Producto producto) {
        try {
            Producto actualizado = productoService.actualizar(id, producto);
            if (actualizado == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Producto no encontrado para actualizar", "id", id));
            }
            return ResponseEntity.ok(Map.of(
                "mensaje", "Producto actualizado exitosamente",
                "producto", actualizado
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", "Error al actualizar producto", "mensaje", e.getMessage()));
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable long id) {
        try {
            Producto eliminado = productoService.eliminar(id);
            if (eliminado == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Producto no encontrado para eliminar", "id", id));
            }
            return ResponseEntity.ok(Map.of(
                "mensaje", "Producto eliminado exitosamente",
                "producto", eliminado
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Error al eliminar producto", "mensaje", e.getMessage()));
        }
    }
}
