package cl.duoc.gestionestudiantes.dao;

import cl.duoc.gestionestudiantes.model.Estudiante;
import java.util.List;

/**
 * Interfaz DAO para la entidad Estudiante.
 * Define las operaciones CRUD que debe implementar cualquier clase DAO de Estudiante.
 */
public interface IEstudianteDAO {
    
    /**
     * CREATE - Registrar un nuevo estudiante
     * @param estudiante Objeto estudiante a registrar
     * @return true si se registró correctamente, false en caso contrario
     */
    boolean create(Estudiante estudiante);
    
    /**
     * READ ALL - Obtener todos los estudiantes
     * @return Lista de estudiantes
     */
    List<Estudiante> readAll();
    
    /**
     * READ BY ID - Obtener un estudiante por su ID
     * @param id ID del estudiante
     * @return Objeto Estudiante o null si no existe
     */
    Estudiante readById(int id);
    
    /**
     * UPDATE - Actualizar datos de un estudiante
     * @param estudiante Objeto estudiante con datos actualizados
     * @return true si se actualizó correctamente, false en caso contrario
     */
    boolean update(Estudiante estudiante);
    
    /**
     * DELETE - Eliminar un estudiante por su ID
     * @param id ID del estudiante a eliminar
     * @return true si se eliminó correctamente, false en caso contrario
     */
    boolean delete(int id);
    
    /**
     * Cerrar la conexión a la base de datos
     */
    void cerrarConexion();
}
