package com.miapp.controllers;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class UsuarioServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.getWriter().println("<h1>Hola desde el servlet Usuario</h1>");
    }
}