package cl.duoc.bibliotecagestion.controller;

import cl.duoc.bibliotecagestion.dao.LibroDAO;
import cl.duoc.bibliotecagestion.dao.ILibroDAO;
import cl.duoc.bibliotecagestion.model.Libro;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * Servlet controlador para gestionar el catálogo de libros.
 * Implementa el patrón MVC manejando peticiones GET para mostrar libros.
 */
public class CatalogoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private ILibroDAO libroDAO;
    
    public CatalogoServlet() {
        System.out.println(">>> CatalogoServlet: Constructor llamado");
    }
    
    @Override
    public void init() throws ServletException {
        System.out.println(">>> CatalogoServlet: init() llamado");
        super.init();
        try {
            this.libroDAO = new LibroDAO();
            System.out.println(">>> CatalogoServlet: LibroDAO inicializado correctamente");
        } catch (Exception e) {
            System.err.println(">>> Error al inicializar LibroDAO: " + e.getMessage());
            throw new ServletException("Error al inicializar el servlet", e);
        }
    }
    
    /**
     * Método doGet para manejar peticiones GET
     * Muestra el catálogo de libros disponibles
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        System.out.println(">>> CatalogoServlet: Procesando solicitud GET");
        
        // Obtener todos los libros (se puede filtrar solo disponibles si se desea)
        List<Libro> libros = libroDAO.readAll();
        
        // Enviar la lista de libros a la vista
        request.setAttribute("libros", libros);
        
        // Redirigir a index.jsp
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
    
    @Override
    public void destroy() {
        System.out.println(">>> CatalogoServlet: destroy() llamado");
        if (libroDAO != null) {
            libroDAO.cerrarConexion();
        }
        super.destroy();
    }
}
