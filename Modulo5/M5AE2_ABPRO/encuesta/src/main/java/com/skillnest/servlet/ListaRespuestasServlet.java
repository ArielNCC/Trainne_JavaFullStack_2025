
/**
 * Servlet que muestra la lista de respuestas de encuestas.
 * Combina respuestas simuladas y las ingresadas por los usuarios.
 * Envía la lista a la vista lista_respuestas.jsp.
 */
package com.skillnest.servlet;

import com.skillnest.model.RespuestaEncuesta;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet para mostrar la lista simulada de respuestas de encuestas
 */
public class ListaRespuestasServlet extends HttpServlet {
    
    /**
     * Atiende las peticiones GET y muestra la lista de respuestas.
     * Combina respuestas simuladas y las ingresadas por los usuarios.
     * @param request petición HTTP
     * @param response respuesta HTTP
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Obtener la lista global de respuestas del ServletContext
        @SuppressWarnings("unchecked")
        List<RespuestaEncuesta> respuestasGlobal =
            (List<RespuestaEncuesta>) getServletContext().getAttribute("respuestasGlobal");
        if (respuestasGlobal == null) {
            respuestasGlobal = new ArrayList<>();
        }

        // Crear lista simulada de respuestas
        List<RespuestaEncuesta> respuestasSimuladas = crearRespuestasSimuladas();

        // Combinar ambas listas: primero los simulados, luego los ingresados
        List<RespuestaEncuesta> respuestas = new ArrayList<>(respuestasSimuladas);
        respuestas.addAll(respuestasGlobal);

        // Pasar la lista combinada a la JSP
        request.setAttribute("respuestas", respuestas);
        request.getRequestDispatcher("lista_respuestas.jsp").forward(request, response);
    }
    
    /**
     * Atiende las peticiones POST reenviando a doGet.
     * @param request petición HTTP
     * @param response respuesta HTTP
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
    
    /**
     * Crea una lista simulada de respuestas para mostrar ejemplos en la vista.
     * @return lista de respuestas simuladas
     */
    private List<RespuestaEncuesta> crearRespuestasSimuladas() {
        List<RespuestaEncuesta> respuestas = new ArrayList<>();
        respuestas.add(new RespuestaEncuesta(
            "María González", 28, "si", 5,
            "Excelente servicio, muy recomendable. La atención fue perfecta y el sitio es muy fácil de usar."
        ));
        respuestas.add(new RespuestaEncuesta(
            "Carlos Rodríguez", 35, "si", 4,
            "Buen sitio, aunque podría mejorar en algunos aspectos de navegación."
        ));
        respuestas.add(new RespuestaEncuesta(
            "Ana López", 17, "no", 2,
            "No me gustó mucho, es confuso."
        ));
        respuestas.add(new RespuestaEncuesta(
            "José Martínez", 42, "si", 3,
            "Regular, cumple con lo básico pero nada extraordinario. Funciona bien para lo que necesito."
        ));
        respuestas.add(new RespuestaEncuesta(
            "Laura Fernández", 25, "si", 5,
            "¡Fantástico! Es exactamente lo que estaba buscando. Muy intuitivo y completo."
        ));
        respuestas.add(new RespuestaEncuesta(
            "Pedro Sánchez", 19, "no", 1,
            "Muy malo, no funciona como debería. Tuve muchos problemas para completar mis tareas."
        ));
        respuestas.add(new RespuestaEncuesta(
            "Carmen Jiménez", 31, "si", 4,
            "Está bien, me ha servido para lo que necesitaba."
        ));
        return respuestas;
    }
}