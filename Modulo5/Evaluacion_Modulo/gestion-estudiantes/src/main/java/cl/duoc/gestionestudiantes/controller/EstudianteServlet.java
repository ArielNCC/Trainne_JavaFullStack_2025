package cl.duoc.gestionestudiantes.controller;

import cl.duoc.gestionestudiantes.dao.EstudianteDAO;
import cl.duoc.gestionestudiantes.dao.IEstudianteDAO;
import cl.duoc.gestionestudiantes.model.Estudiante;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

/**
 * Servlet controlador para gestionar las operaciones CRUD de estudiantes.
 * Implementa el patrón MVC manejando peticiones GET y POST.
 */
public class EstudianteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private IEstudianteDAO estudianteDAO;
    
    public EstudianteServlet() {
        System.out.println(">>> EstudianteServlet: Constructor llamado");
    }
    
    @Override
    public void init() throws ServletException {
        System.out.println(">>> EstudianteServlet: init() llamado");
        super.init();
        try {
            estudianteDAO = new EstudianteDAO();
            System.out.println(">>> EstudianteServlet: DAO inicializado");
        } catch (Exception e) {
            System.err.println(">>> EstudianteServlet: Error en init: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Método doGet para manejar peticiones GET
     * Muestra el formulario y la tabla de estudiantes
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String pathInfo = request.getPathInfo();
        
        if (pathInfo == null || pathInfo.equals("/")) {
            // Listar todos los estudiantes
            listarEstudiantes(request, response);
        } else if (pathInfo.equals("/nuevo")) {
            // Mostrar formulario para nuevo estudiante
            mostrarFormularioNuevo(request, response);
        } else if (pathInfo.startsWith("/editar/")) {
            // Mostrar formulario de edición
            String idStr = pathInfo.substring(8);
            mostrarFormularioEditar(request, response, idStr);
        } else if (pathInfo.startsWith("/eliminar/")) {
            // Eliminar estudiante
            String idStr = pathInfo.substring(10);
            eliminarEstudiante(request, response, idStr);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
    
    /**
     * Método doPost para manejar peticiones POST
     * Procesa formularios de alta y edición
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Configurar codificación para caracteres especiales
        request.setCharacterEncoding("UTF-8");
        
        String action = request.getParameter("action");
        
        if ("crear".equals(action)) {
            crearEstudiante(request, response);
        } else if ("actualizar".equals(action)) {
            actualizarEstudiante(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
    
    /**
     * Lista todos los estudiantes
     */
    private void listarEstudiantes(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        List<Estudiante> estudiantes = estudianteDAO.readAll();
        request.setAttribute("estudiantes", estudiantes);
        
        // Obtener mensajes de sesión si existen
        HttpSession session = request.getSession();
        String mensaje = (String) session.getAttribute("mensaje");
        String tipoMensaje = (String) session.getAttribute("tipoMensaje");
        
        if (mensaje != null) {
            request.setAttribute("mensaje", mensaje);
            request.setAttribute("tipoMensaje", tipoMensaje);
            session.removeAttribute("mensaje");
            session.removeAttribute("tipoMensaje");
        }
        
        request.getRequestDispatcher("/WEB-INF/vistas/listaEstudiantes.jsp").forward(request, response);
    }
    
    /**
     * Muestra el formulario para nuevo estudiante
     */
    private void mostrarFormularioNuevo(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.setAttribute("accion", "nuevo");
        request.getRequestDispatcher("/WEB-INF/vistas/formularioNuevo.jsp").forward(request, response);
    }
    
    /**
     * Muestra el formulario de edición con los datos del estudiante
     */
    private void mostrarFormularioEditar(HttpServletRequest request, HttpServletResponse response, String idStr) 
            throws ServletException, IOException {
        
        try {
            int id = Integer.parseInt(idStr);
            Estudiante estudiante = estudianteDAO.readById(id);
            
            if (estudiante != null) {
                request.setAttribute("estudiante", estudiante);
                request.setAttribute("accion", "editar");
                request.getRequestDispatcher("/WEB-INF/vistas/formularioEditar.jsp").forward(request, response);
            } else {
                HttpSession session = request.getSession();
                session.setAttribute("mensaje", "Estudiante no encontrado");
                session.setAttribute("tipoMensaje", "error");
                response.sendRedirect(request.getContextPath() + "/estudiantes");
            }
            
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID inválido");
        }
    }
    
    /**
     * Crea un nuevo estudiante
     */
    private void crearEstudiante(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        
        String nombreCompleto = request.getParameter("nombreCompleto");
        String correoElectronico = request.getParameter("correoElectronico");
        String carrera = request.getParameter("carrera");
        
        Estudiante estudiante = new Estudiante(nombreCompleto, correoElectronico, carrera);
        
        HttpSession session = request.getSession();
        
        if (estudianteDAO.create(estudiante)) {
            session.setAttribute("mensaje", "Estudiante registrado exitosamente");
            session.setAttribute("tipoMensaje", "success");
        } else {
            session.setAttribute("mensaje", "Error al registrar estudiante");
            session.setAttribute("tipoMensaje", "error");
        }
        
        response.sendRedirect(request.getContextPath() + "/estudiantes");
    }
    
    /**
     * Actualiza un estudiante existente
     */
    private void actualizarEstudiante(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String nombreCompleto = request.getParameter("nombreCompleto");
            String correoElectronico = request.getParameter("correoElectronico");
            String carrera = request.getParameter("carrera");
            
            Estudiante estudiante = new Estudiante(id, nombreCompleto, correoElectronico, carrera);
            
            HttpSession session = request.getSession();
            
            if (estudianteDAO.update(estudiante)) {
                session.setAttribute("mensaje", "Estudiante actualizado exitosamente");
                session.setAttribute("tipoMensaje", "success");
            } else {
                session.setAttribute("mensaje", "Error al actualizar estudiante");
                session.setAttribute("tipoMensaje", "error");
            }
            
            response.sendRedirect(request.getContextPath() + "/estudiantes");
            
        } catch (NumberFormatException e) {
            HttpSession session = request.getSession();
            session.setAttribute("mensaje", "ID inválido");
            session.setAttribute("tipoMensaje", "error");
            response.sendRedirect(request.getContextPath() + "/estudiantes");
        }
    }
    
    /**
     * Elimina un estudiante
     */
    private void eliminarEstudiante(HttpServletRequest request, HttpServletResponse response, String idStr) 
            throws IOException {
        
        try {
            int id = Integer.parseInt(idStr);
            
            HttpSession session = request.getSession();
            
            if (estudianteDAO.delete(id)) {
                session.setAttribute("mensaje", "Estudiante eliminado exitosamente");
                session.setAttribute("tipoMensaje", "success");
            } else {
                session.setAttribute("mensaje", "Error al eliminar estudiante");
                session.setAttribute("tipoMensaje", "error");
            }
            
        } catch (NumberFormatException e) {
            HttpSession session = request.getSession();
            session.setAttribute("mensaje", "ID inválido");
            session.setAttribute("tipoMensaje", "error");
        }
        
        response.sendRedirect(request.getContextPath() + "/estudiantes");
    }
    
    @Override
    public void destroy() {
        if (estudianteDAO != null) {
            estudianteDAO.cerrarConexion();
        }
        super.destroy();
    }
}
