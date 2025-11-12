package com.skillnest.formulario_demo.servlet;

import com.skillnest.formulario_demo.dao.ProductoDAO;
import com.skillnest.formulario_demo.dao.ProductoDAOImp;
import com.skillnest.formulario_demo.model.Producto;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/registrarProducto")
public class RegistrarProductoServlet extends HttpServlet {
    
    private ProductoDAO productoDAO;

    @Override
    public void init() throws ServletException {
        productoDAO = new ProductoDAOImp();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Configurar encoding para caracteres especiales
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        // Obtener parámetros del formulario
        String nombre = request.getParameter("nombre");
        String precioStr = request.getParameter("precio");
        String descripcion = request.getParameter("descripcion");
        
        try {
            // Validar que los campos no estén vacíos
            if (nombre == null || nombre.trim().isEmpty() || 
                precioStr == null || precioStr.trim().isEmpty()) {
                request.setAttribute("error", "El nombre y el precio son obligatorios");
                request.getRequestDispatcher("/formulario.jsp").forward(request, response);
                return;
            }
            
            // Convertir precio a double
            double precio = Double.parseDouble(precioStr);
            
            // Validar que el precio sea positivo
            if (precio <= 0) {
                request.setAttribute("error", "El precio debe ser mayor a cero");
                request.getRequestDispatcher("/formulario.jsp").forward(request, response);
                return;
            }
            
            // Crear el objeto Producto
            Producto producto = new Producto(nombre, precio, descripcion);
            
            // Guardar en la base de datos
            boolean resultado = productoDAO.crear(producto);
            
            if (resultado) {
                // Redirigir a la lista de productos
                response.sendRedirect(request.getContextPath() + "/listarProductos");
            } else {
                request.setAttribute("error", "Error al registrar el producto");
                request.getRequestDispatcher("/formulario.jsp").forward(request, response);
            }
            
        } catch (NumberFormatException e) {
            request.setAttribute("error", "El precio debe ser un número válido");
            request.getRequestDispatcher("/formulario.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Redirigir al formulario
        request.getRequestDispatcher("/formulario.jsp").forward(request, response);
    }
}
