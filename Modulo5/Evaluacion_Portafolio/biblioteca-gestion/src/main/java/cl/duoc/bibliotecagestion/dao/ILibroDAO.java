package cl.duoc.bibliotecagestion.dao;

import cl.duoc.bibliotecagestion.model.Libro;
import java.util.List;

/**
 * Interfaz DAO para la entidad Libro.
 * Define las operaciones CRUD que debe implementar cualquier clase DAO de Libro.
 */
public interface ILibroDAO {
    
    /**
     * CREATE - Registrar un nuevo libro
     * @param libro Objeto libro a registrar
     * @return true si se registró correctamente, false en caso contrario
     */
    boolean create(Libro libro);
    
    /**
     * READ ALL - Obtener todos los libros
     * @return Lista de libros
     */
    List<Libro> readAll();
    
    /**
     * READ DISPONIBLES - Obtener solo libros disponibles
     * @return Lista de libros disponibles
     */
    List<Libro> readDisponibles();
    
    /**
     * READ BY ID - Obtener un libro por su ID
     * @param id ID del libro
     * @return Objeto Libro o null si no existe
     */
    Libro readById(int id);
    
    /**
     * UPDATE - Actualizar datos de un libro
     * @param libro Objeto libro con datos actualizados
     * @return true si se actualizó correctamente, false en caso contrario
     */
    boolean update(Libro libro);
    
    /**
     * DELETE - Eliminar un libro por su ID
     * @param id ID del libro a eliminar
     * @return true si se eliminó correctamente, false en caso contrario
     */
    boolean delete(int id);
    
    /**
     * Cerrar la conexión a la base de datos
     */
    void cerrarConexion();
}
