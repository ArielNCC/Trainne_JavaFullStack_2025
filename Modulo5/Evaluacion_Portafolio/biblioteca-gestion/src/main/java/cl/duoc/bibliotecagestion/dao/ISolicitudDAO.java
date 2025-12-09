package cl.duoc.bibliotecagestion.dao;

import cl.duoc.bibliotecagestion.model.Solicitud;
import java.util.List;

/**
 * Interfaz DAO para la entidad Solicitud de Préstamo.
 * Define las operaciones CRUD que debe implementar cualquier clase DAO de Solicitud.
 */
public interface ISolicitudDAO {
    
    /**
     * CREATE - Registrar una nueva solicitud de préstamo
     * @param solicitud Objeto solicitud a registrar
     * @return true si se registró correctamente, false en caso contrario
     */
    boolean create(Solicitud solicitud);
    
    /**
     * READ ALL - Obtener todas las solicitudes con información del libro
     * @return Lista de solicitudes
     */
    List<Solicitud> readAll();
    
    /**
     * READ BY ID - Obtener una solicitud por su ID
     * @param id ID de la solicitud
     * @return Objeto Solicitud o null si no existe
     */
    Solicitud readById(int id);
    
    /**
     * UPDATE - Actualizar datos de una solicitud (principalmente el estado)
     * @param solicitud Objeto solicitud con datos actualizados
     * @return true si se actualizó correctamente, false en caso contrario
     */
    boolean update(Solicitud solicitud);
    
    /**
     * DELETE - Eliminar una solicitud por su ID
     * @param id ID de la solicitud a eliminar
     * @return true si se eliminó correctamente, false en caso contrario
     */
    boolean delete(int id);
    
    /**
     * Cerrar la conexión a la base de datos
     */
    void cerrarConexion();
}
