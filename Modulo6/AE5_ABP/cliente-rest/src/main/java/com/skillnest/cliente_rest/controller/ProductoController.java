package com.skillnest.cliente_rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.skillnest.cliente_rest.model.Producto;
import com.skillnest.cliente_rest.model.ProductoDto;
import com.skillnest.cliente_rest.service.ProductoService;

@Controller
@RequestMapping("/productos")
public class ProductoController {
	
    @Autowired
    private ProductoService productoService;
    
    // Redirigir /productos a /productos/listar
    @GetMapping
    public String redirectToListar() {
        return "redirect:/productos/listar";
    }
    
	//http://localhost:8081/productos/listar
    @GetMapping("/listar")
    public String listarProductos(Model model) {
    	List<Producto> lista_productos= productoService.listarTodos();
    	model.addAttribute("lista_productos", lista_productos);
  
        return "productos/listar";//hacia un jsp
    }

    @GetMapping("/crear")
    public String formularioCrear() {
        return "productos/crear";//hacia un jsp
    }

    @PostMapping("/guardar")
    public String guardarProducto(@RequestParam String nombre, @RequestParam double precio,Model model) {
        // Procesar los datos
    	String mensaje = productoService.formatearProducto(nombre, precio);
    	//instancia de la clase ProductoDto
    	ProductoDto productoDto = new ProductoDto(nombre,"",0,precio);
    	Producto almacenado =productoService.guardaProducto(productoDto);
    	
    	model.addAttribute("mensaje", mensaje);
        model.addAttribute("nombre", almacenado.getNombre());
        model.addAttribute("producto", almacenado);
        
        return "productos/detalle";
        //return "redirect:/productos/listar";//http://localhost:8081/productos/listar

    }
    
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
         Producto producto = productoService.obtener(id);
         model.addAttribute("producto",producto);
         return "productos/editar";
    }
    
    @GetMapping("/detalle/{id}")
    public String verDetalleConId(@PathVariable Long id, Model model) {
        Producto producto = productoService.obtener(id);
        model.addAttribute("producto", producto);
        return "productos/detalle";
    }
    
    @GetMapping("/detalle")
    public String verDetalle(Model model) {
        model.addAttribute("mensaje", "Producto destacado");
        return "productos/detalle";//hacia un jsp
    }

    @GetMapping("/mensaje")
    public String mostrarMensaje(Model model) {
        model.addAttribute("mensaje", productoService.obtenerMensaje());
        return "productos/detalle"; // Renderiza 
    }

    @PostMapping("/actualizar")
    public String actualizarProducto(@RequestParam Long id, @RequestParam String nombre, 
                                     @RequestParam double precio, @RequestParam(required = false) String descripcion, 
                                     Model model) {
        Producto producto = new Producto(nombre, precio, descripcion);
        producto.setId(id);
        Producto actualizado = productoService.actualizar(id, producto);
        
        model.addAttribute("mensaje", "Producto actualizado exitosamente");
        model.addAttribute("producto", actualizado);
        
        return "productos/detalle";
    }
    
    @PostMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Long id, Model model) {
        productoService.eliminar(id);
        return "redirect:/productos/listar";
    }
}