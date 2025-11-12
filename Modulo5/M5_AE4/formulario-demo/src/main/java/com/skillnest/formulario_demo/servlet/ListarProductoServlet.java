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
import java.util.List;

@WebServlet("/listarProductos")
public class ListarProductoServlet extends HttpServlet {
    
    private ProductoDAO productoDAO;

    @Override
    public void init() throws ServletException {
        productoDAO = new ProductoDAOImp();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Configurar encoding
        response.setCharacterEncoding("UTF-8");
        
        // Obtener todos los productos
        List<Producto> productos = productoDAO.obtenerTodos();
        
        // Pasar la lista de productos a la JSP
        request.setAttribute("productos", productos);
        
        // Verificar si hay un mensaje de éxito
        String mensaje = request.getParameter("mensaje");
        if (mensaje != null) {
            request.setAttribute("mensaje", mensaje);
        }
        
        // Redirigir a la vista
        request.getRequestDispatcher("/listaProductos.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}
