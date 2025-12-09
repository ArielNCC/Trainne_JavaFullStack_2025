package cl.duoc.bibliotecagestion.dao;

import cl.duoc.bibliotecagestion.model.Solicitud;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para la entidad Solicitud de Préstamo.
 * Implementa operaciones CRUD utilizando JDBC.
 */
public class SolicitudDAO implements ISolicitudDAO {
    
    private Connection conexion;
    
    /**
     * Constructor que inicializa la conexión a la base de datos
     */
    public SolicitudDAO() {
        try {
            this.conexion = ConexionBD.obtenerConexion();
        } catch (SQLException e) {
            System.err.println("✗ Error al inicializar SolicitudDAO: " + e.getMessage());
        }
    }
    
    /**
     * CREATE - Registrar una nueva solicitud de préstamo
     */
    @Override
    public boolean create(Solicitud solicitud) {
        String sql = "INSERT INTO solicitudes_prestamo (nombre_usuario, correo_usuario, libro_id, estado) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement statement = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, solicitud.getNombreUsuario());
            statement.setString(2, solicitud.getCorreoUsuario());
            statement.setInt(3, solicitud.getLibroId());
            statement.setString(4, solicitud.getEstado() != null ? solicitud.getEstado() : "PENDIENTE");
            
            int filasAfectadas = statement.executeUpdate();
            
            if (filasAfectadas > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    solicitud.setId(generatedKeys.getInt(1));
                    System.out.println("✓ Solicitud registrada con ID: " + solicitud.getId());
                }
                return true;
            }
            return false;
            
        } catch (SQLException e) {
            System.err.println("✗ Error al registrar solicitud: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * READ ALL - Consultar todas las solicitudes con información del libro
     */
    @Override
    public List<Solicitud> readAll() {
        List<Solicitud> solicitudes = new ArrayList<>();
        String sql = "SELECT s.id, s.nombre_usuario, s.correo_usuario, s.libro_id, " +
                    "s.fecha_solicitud, s.estado, l.titulo, l.autor " +
                    "FROM solicitudes_prestamo s " +
                    "LEFT JOIN libros l ON s.libro_id = l.id " +
                    "ORDER BY s.fecha_solicitud DESC";
        
        try (Statement statement = conexion.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            
            while (resultSet.next()) {
                Solicitud solicitud = new Solicitud(
                    resultSet.getInt("id"),
                    resultSet.getString("nombre_usuario"),
                    resultSet.getString("correo_usuario"),
                    resultSet.getInt("libro_id"),
                    resultSet.getTimestamp("fecha_solicitud"),
                    resultSet.getString("estado")
                );
                
                // Agregar información del libro
                solicitud.setTituloLibro(resultSet.getString("titulo"));
                solicitud.setAutorLibro(resultSet.getString("autor"));
                
                solicitudes.add(solicitud);
            }
            
            System.out.println("✓ Se obtuvieron " + solicitudes.size() + " solicitudes");
            
        } catch (SQLException e) {
            System.err.println("✗ Error al consultar solicitudes: " + e.getMessage());
        }
        
        return solicitudes;
    }
    
    /**
     * READ BY ID - Consultar una solicitud por su ID
     */
    @Override
    public Solicitud readById(int id) {
        String sql = "SELECT s.id, s.nombre_usuario, s.correo_usuario, s.libro_id, " +
                    "s.fecha_solicitud, s.estado, l.titulo, l.autor " +
                    "FROM solicitudes_prestamo s " +
                    "LEFT JOIN libros l ON s.libro_id = l.id " +
                    "WHERE s.id = ?";
        
        try (PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, id);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Solicitud solicitud = new Solicitud(
                        resultSet.getInt("id"),
                        resultSet.getString("nombre_usuario"),
                        resultSet.getString("correo_usuario"),
                        resultSet.getInt("libro_id"),
                        resultSet.getTimestamp("fecha_solicitud"),
                        resultSet.getString("estado")
                    );
                    
                    solicitud.setTituloLibro(resultSet.getString("titulo"));
                    solicitud.setAutorLibro(resultSet.getString("autor"));
                    
                    System.out.println("✓ Solicitud encontrada: " + solicitud.getId());
                    return solicitud;
                }
            }
            
        } catch (SQLException e) {
            System.err.println("✗ Error al buscar solicitud: " + e.getMessage());
        }
        
        System.out.println("✗ No se encontró solicitud con ID: " + id);
        return null;
    }
    
    /**
     * UPDATE - Actualizar los datos de una solicitud
     */
    @Override
    public boolean update(Solicitud solicitud) {
        String sql = "UPDATE solicitudes_prestamo SET nombre_usuario = ?, correo_usuario = ?, " +
                    "libro_id = ?, estado = ? WHERE id = ?";
        
        try (PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setString(1, solicitud.getNombreUsuario());
            statement.setString(2, solicitud.getCorreoUsuario());
            statement.setInt(3, solicitud.getLibroId());
            statement.setString(4, solicitud.getEstado());
            statement.setInt(5, solicitud.getId());
            
            int filasAfectadas = statement.executeUpdate();
            
            if (filasAfectadas > 0) {
                System.out.println("✓ Solicitud actualizada: " + solicitud.getId());
                return true;
            }
            
            System.out.println("✗ No se pudo actualizar la solicitud con ID: " + solicitud.getId());
            return false;
            
        } catch (SQLException e) {
            System.err.println("✗ Error al actualizar solicitud: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * DELETE - Eliminar una solicitud por su ID
     */
    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM solicitudes_prestamo WHERE id = ?";
        
        try (PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, id);
            
            int filasAfectadas = statement.executeUpdate();
            
            if (filasAfectadas > 0) {
                System.out.println("✓ Solicitud eliminada con ID: " + id);
                return true;
            }
            
            System.out.println("✗ No se encontró solicitud con ID: " + id);
            return false;
            
        } catch (SQLException e) {
            System.err.println("✗ Error al eliminar solicitud: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Método para cerrar la conexión
     */
    @Override
    public void cerrarConexion() {
        ConexionBD.cerrarConexion();
    }
}
