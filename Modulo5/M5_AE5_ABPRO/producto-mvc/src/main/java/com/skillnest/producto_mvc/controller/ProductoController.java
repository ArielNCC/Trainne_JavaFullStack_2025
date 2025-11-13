package com.skillnest.producto_mvc.controller;

import com.skillnest.producto_mvc.model.Producto;
import com.skillnest.producto_mvc.model.Usuario;
import com.skillnest.producto_mvc.service.ProductoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/productos")
public class ProductoController {
    
    @Autowired
    private ProductoService productoService;
    
    // Verificar sesión
    private boolean verificarSesion(HttpSession session) {
        return session.getAttribute("usuario") != null;
    }
    
    // Listar todos los productos
    @GetMapping
    public String listarProductos(HttpSession session, Model model) {
        if (!verificarSesion(session)) {
            return "redirect:/login";
        }
        
        List<Producto> productos = productoService.listarTodos();
        model.addAttribute("productos", productos);
        model.addAttribute("nombreUsuario", ((Usuario) session.getAttribute("usuario")).getNombreCompleto());
        return "productos/lista";
    }
    
    // Mostrar formulario para nuevo producto
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(HttpSession session, Model model) {
        if (!verificarSesion(session)) {
            return "redirect:/login";
        }
        
        model.addAttribute("producto", new Producto());
        model.addAttribute("nombreUsuario", ((Usuario) session.getAttribute("usuario")).getNombreCompleto());
        return "productos/formulario";
    }
    
    // Guardar nuevo producto
    @PostMapping("/guardar")
    public String guardarProducto(
            @RequestParam String nombre,
            @RequestParam String descripcion,
            @RequestParam BigDecimal precio,
            @RequestParam Integer stock,
            @RequestParam String categoria,
            HttpSession session) {
        
        if (!verificarSesion(session)) {
            return "redirect:/login";
        }
        
        Producto producto = new Producto(nombre, descripcion, precio, stock, categoria);
        productoService.guardar(producto);
        
        return "redirect:/productos";
    }
    
    // Mostrar formulario de edición
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Integer id, HttpSession session, Model model) {
        if (!verificarSesion(session)) {
            return "redirect:/login";
        }
        
        Producto producto = productoService.buscarPorId(id);
        if (producto == null) {
            return "redirect:/productos";
        }
        
        model.addAttribute("producto", producto);
        model.addAttribute("nombreUsuario", ((Usuario) session.getAttribute("usuario")).getNombreCompleto());
        return "productos/formulario";
    }
    
    // Actualizar producto
    @PostMapping("/actualizar")
    public String actualizarProducto(
            @RequestParam Integer id,
            @RequestParam String nombre,
            @RequestParam String descripcion,
            @RequestParam BigDecimal precio,
            @RequestParam Integer stock,
            @RequestParam String categoria,
            @RequestParam Boolean activo,
            HttpSession session) {
        
        if (!verificarSesion(session)) {
            return "redirect:/login";
        }
        
        Producto producto = productoService.buscarPorId(id);
        if (producto != null) {
            producto.setNombre(nombre);
            producto.setDescripcion(descripcion);
            producto.setPrecio(precio);
            producto.setStock(stock);
            producto.setCategoria(categoria);
            producto.setActivo(activo);
            productoService.actualizar(producto);
        }
        
        return "redirect:/productos";
    }
    
    // Eliminar producto
    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Integer id, HttpSession session) {
        if (!verificarSesion(session)) {
            return "redirect:/login";
        }
        
        productoService.eliminar(id);
        return "redirect:/productos";
    }
    
    // Ver detalle de producto
    @GetMapping("/detalle/{id}")
    public String verDetalle(@PathVariable Integer id, HttpSession session, Model model) {
        if (!verificarSesion(session)) {
            return "redirect:/login";
        }
        
        Producto producto = productoService.buscarPorId(id);
        if (producto == null) {
            return "redirect:/productos";
        }
        
        model.addAttribute("producto", producto);
        model.addAttribute("nombreUsuario", ((Usuario) session.getAttribute("usuario")).getNombreCompleto());
        return "productos/detalle";
    }
}
