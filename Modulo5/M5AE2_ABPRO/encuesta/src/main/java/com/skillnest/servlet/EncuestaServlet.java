
/**
 * Servlet que procesa las respuestas del formulario de encuesta.
 * Recibe los datos del usuario, los valida y los almacena en una lista global.
 * Luego muestra los resultados individuales en la vista resultados.jsp.
 */
package com.skillnest.servlet;

import com.skillnest.model.RespuestaEncuesta;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet para procesar las respuestas de la encuesta
 */
public class EncuestaServlet extends HttpServlet {
    
    /**
     * Redirige las peticiones GET a la página de encuesta.
     * @param request petición HTTP
     * @param response respuesta HTTP
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("encuesta.jsp");
    }
    
    /**
     * Procesa el envío del formulario de encuesta (POST).
     * Valida los datos, los almacena en la lista global y muestra los resultados.
     * @param request petición HTTP
     * @param response respuesta HTTP
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        try {
            // Obtener datos del formulario
            String nombre = request.getParameter("nombre");
            String edadStr = request.getParameter("edad");
            String recomendaria = request.getParameter("recomendaria");
            String calificacionStr = request.getParameter("calificacion");
            String comentario = request.getParameter("comentario");

            // Validar datos básicos
            if (nombre == null || nombre.trim().isEmpty()) {
                request.setAttribute("error", "El nombre es obligatorio");
                request.getRequestDispatcher("encuesta.jsp").forward(request, response);
                return;
            }

            int edad = 0;
            int calificacion = 0;

            try {
                edad = Integer.parseInt(edadStr);
                calificacion = Integer.parseInt(calificacionStr);
            } catch (NumberFormatException e) {
                request.setAttribute("error", "La edad y calificación deben ser números válidos");
                request.getRequestDispatcher("encuesta.jsp").forward(request, response);
                return;
            }

            // Crear objeto de respuesta
            RespuestaEncuesta respuesta = new RespuestaEncuesta(
                nombre.trim(),
                edad,
                recomendaria,
                calificacion,
                comentario != null ? comentario.trim() : ""
            );

            // Guardar la respuesta en la lista global (ServletContext)
            synchronized (getServletContext()) {
                @SuppressWarnings("unchecked")
                java.util.List<RespuestaEncuesta> respuestasGlobal =
                    (java.util.List<RespuestaEncuesta>) getServletContext().getAttribute("respuestasGlobal");
                if (respuestasGlobal == null) {
                    respuestasGlobal = new java.util.ArrayList<>();
                    getServletContext().setAttribute("respuestasGlobal", respuestasGlobal);
                }
                respuestasGlobal.add(respuesta);
            }

            // Pasar datos a la vista de resultados
            request.setAttribute("respuesta", respuesta);
            request.getRequestDispatcher("resultados.jsp").forward(request, response);

        } catch (Exception e) {
            request.setAttribute("error", "Error al procesar la encuesta: " + e.getMessage());
            request.getRequestDispatcher("encuesta.jsp").forward(request, response);
        }
    }
}