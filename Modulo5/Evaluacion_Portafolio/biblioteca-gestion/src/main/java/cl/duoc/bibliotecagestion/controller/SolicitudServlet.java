package cl.duoc.bibliotecagestion.controller;

import cl.duoc.bibliotecagestion.dao.LibroDAO;
import cl.duoc.bibliotecagestion.dao.SolicitudDAO;
import cl.duoc.bibliotecagestion.dao.ILibroDAO;
import cl.duoc.bibliotecagestion.dao.ISolicitudDAO;
import cl.duoc.bibliotecagestion.model.Libro;
import cl.duoc.bibliotecagestion.model.Solicitud;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

/**
 * Servlet controlador para gestionar solicitudes de préstamo.
 * Implementa el patrón MVC manejando peticiones GET y POST.
 */
public class SolicitudServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private ISolicitudDAO solicitudDAO;
    private ILibroDAO libroDAO;
    
    public SolicitudServlet() {
        System.out.println(">>> SolicitudServlet: Constructor llamado");
    }
    
    @Override
    public void init() throws ServletException {
        System.out.println(">>> SolicitudServlet: init() llamado");
        super.init();
        try {
            this.solicitudDAO = new SolicitudDAO();
            this.libroDAO = new LibroDAO();
            System.out.println(">>> SolicitudServlet: DAOs inicializados correctamente");
        } catch (Exception e) {
            System.err.println(">>> Error al inicializar SolicitudServlet: " + e.getMessage());
            throw new ServletException("Error al inicializar el servlet", e);
        }
    }
    
    /**
     * Método doGet para manejar peticiones GET
     * Muestra el formulario de solicitud o la página de administración
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String pathInfo = request.getPathInfo();
        
        if (pathInfo == null || pathInfo.equals("/")) {
            // Mostrar formulario de solicitud
            mostrarFormularioSolicitud(request, response);
        } else if (pathInfo.equals("/admin")) {
            // Mostrar página de administración con todas las solicitudes
            mostrarPaginaAdmin(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/catalogo");
        }
    }
    
    /**
     * Método doPost para manejar peticiones POST
     * Procesa el formulario de solicitud
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Configurar codificación para caracteres especiales
        request.setCharacterEncoding("UTF-8");
        
        String action = request.getParameter("action");
        
        if ("crear".equals(action)) {
            crearSolicitud(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/catalogo");
        }
    }
    
    /**
     * Muestra el formulario para nueva solicitud de préstamo
     */
    private void mostrarFormularioSolicitud(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Obtener libros disponibles para el formulario
        List<Libro> libros = libroDAO.readAll();
        request.setAttribute("libros", libros);
        
        request.getRequestDispatcher("/WEB-INF/vistas/solicitud.jsp").forward(request, response);
    }
    
    /**
     * Crea una nueva solicitud de préstamo
     */
    private void crearSolicitud(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, ServletException {
        
        // Validar campos del formulario
        String nombreUsuario = request.getParameter("nombreUsuario");
        String correoUsuario = request.getParameter("correoUsuario");
        String libroIdStr = request.getParameter("libroId");
        
        if (nombreUsuario == null || nombreUsuario.trim().isEmpty() ||
            correoUsuario == null || correoUsuario.trim().isEmpty() ||
            libroIdStr == null || libroIdStr.trim().isEmpty()) {
            
            HttpSession session = request.getSession();
            session.setAttribute("mensaje", "Todos los campos son obligatorios");
            session.setAttribute("tipoMensaje", "error");
            response.sendRedirect(request.getContextPath() + "/solicitudes");
            return;
        }
        
        try {
            int libroId = Integer.parseInt(libroIdStr);
            
            // Verificar que el libro existe
            Libro libro = libroDAO.readById(libroId);
            if (libro == null) {
                HttpSession session = request.getSession();
                session.setAttribute("mensaje", "El libro seleccionado no existe");
                session.setAttribute("tipoMensaje", "error");
                response.sendRedirect(request.getContextPath() + "/solicitudes");
                return;
            }
            
            // Crear la solicitud
            Solicitud solicitud = new Solicitud(nombreUsuario.trim(), correoUsuario.trim(), libroId);
            
            boolean registrado = solicitudDAO.create(solicitud);
            
            if (registrado) {
                // Guardar información para la página de confirmación
                request.setAttribute("solicitud", solicitud);
                request.setAttribute("libro", libro);
                request.getRequestDispatcher("/WEB-INF/vistas/confirmacion.jsp").forward(request, response);
            } else {
                HttpSession session = request.getSession();
                session.setAttribute("mensaje", "Error al registrar la solicitud. Intente nuevamente.");
                session.setAttribute("tipoMensaje", "error");
                response.sendRedirect(request.getContextPath() + "/solicitudes");
            }
            
        } catch (NumberFormatException e) {
            HttpSession session = request.getSession();
            session.setAttribute("mensaje", "ID de libro inválido");
            session.setAttribute("tipoMensaje", "error");
            response.sendRedirect(request.getContextPath() + "/solicitudes");
        }
    }
    
    /**
     * Muestra la página de administración con todas las solicitudes
     */
    private void mostrarPaginaAdmin(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        List<Solicitud> solicitudes = solicitudDAO.readAll();
        request.setAttribute("solicitudes", solicitudes);
        
        request.getRequestDispatcher("/WEB-INF/vistas/admin.jsp").forward(request, response);
    }
    
    @Override
    public void destroy() {
        System.out.println(">>> SolicitudServlet: destroy() llamado");
        if (solicitudDAO != null) {
            solicitudDAO.cerrarConexion();
        }
        if (libroDAO != null) {
            libroDAO.cerrarConexion();
        }
        super.destroy();
    }
}
