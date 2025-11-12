package com.skillnest.formulario_demo.servlet;

import com.skillnest.formulario_demo.dao.ProductoDAO;
import com.skillnest.formulario_demo.dao.ProductoDAOImp;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/eliminarProducto")
public class EliminarProductoServlet extends HttpServlet {
    
    private ProductoDAO productoDAO;

    @Override
    public void init() throws ServletException {
        productoDAO = new ProductoDAOImp();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Obtener el ID del producto a eliminar
        String idStr = request.getParameter("id");
        
        if (idStr != null && !idStr.trim().isEmpty()) {
            try {
                int id = Integer.parseInt(idStr);
                
                // Eliminar el producto
                boolean resultado = productoDAO.eliminar(id);
                
                if (resultado) {
                    // Redirigir con mensaje de éxito
                    response.sendRedirect(request.getContextPath() + "/listarProductos?mensaje=eliminado");
                } else {
                    // Redirigir con mensaje de error
                    response.sendRedirect(request.getContextPath() + "/listarProductos?mensaje=error");
                }
                
            } catch (NumberFormatException e) {
                response.sendRedirect(request.getContextPath() + "/listarProductos?mensaje=error");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/listarProductos");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}
