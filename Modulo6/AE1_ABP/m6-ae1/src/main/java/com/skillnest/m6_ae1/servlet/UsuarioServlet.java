package com.skillnest.m6_ae1.servlet;

import com.skillnest.m6_ae1.model.Usuario;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioServlet extends HttpServlet {

    private static final String ATTR = "servletUsuarios";

    private boolean esEntradaValida(String nombre, String edadParam) {
        if (nombre == null || nombre.trim().isEmpty()) return false;
        
        try {
            int edad = Integer.parseInt(edadParam.trim());
            return edad >= 0;
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    private List<Usuario> obtenerUsuarios(ServletContext context) {
        List<Usuario> usuarios = (List<Usuario>) context.getAttribute(ATTR);
        if (usuarios == null) {
            usuarios = new ArrayList<>();
            context.setAttribute(ATTR, usuarios);
        }
        return usuarios;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nombre = req.getParameter("nombre");
        String edadParam = req.getParameter("edad");
        
        if (!esEntradaValida(nombre, edadParam)) {
            resp.sendRedirect(req.getContextPath() + "/usuario/form");
            return;
        }

        Integer edad = Integer.parseInt(edadParam.trim());

        // Obtener o crear lista de usuarios
        List<Usuario> usuarios = obtenerUsuarios(req.getServletContext());
        usuarios.add(new Usuario(nombre, edad));

        // redirect to the controller view page
        resp.sendRedirect(req.getContextPath() + "/usuario/ver");
    }

}
