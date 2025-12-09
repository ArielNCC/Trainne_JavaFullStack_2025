package cl.duoc.gestionestudiantes.dao;

import cl.duoc.gestionestudiantes.model.Estudiante;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para la entidad Estudiante.
 * Implementa operaciones CRUD utilizando JDBC.
 */
public class EstudianteDAO implements IEstudianteDAO {
    
    private Connection conexion;
    
    /**
     * Constructor que inicializa la conexión a la base de datos
     */
    public EstudianteDAO() {
        try {
            this.conexion = ConexionBD.obtenerConexion();
        } catch (SQLException e) {
            System.err.println("✗ Error al inicializar EstudianteDAO: " + e.getMessage());
        }
    }
    
    /**
     * CREATE - Registrar un nuevo estudiante en la base de datos
     * 
     * @param estudiante Objeto estudiante a registrar
     * @return true si se registró correctamente, false en caso contrario
     */
    @Override
    public boolean create(Estudiante estudiante) {
        String sql = "INSERT INTO estudiantes (nombre_completo, correo_electronico, carrera) VALUES (?, ?, ?)";
        
        try (PreparedStatement statement = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, estudiante.getNombreCompleto());
            statement.setString(2, estudiante.getCorreoElectronico());
            statement.setString(3, estudiante.getCarrera());
            
            int filasAfectadas = statement.executeUpdate();
            
            if (filasAfectadas > 0) {
                // Obtener el ID generado automáticamente
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        estudiante.setId(generatedKeys.getInt(1));
                    }
                }
                System.out.println("✓ Estudiante registrado con ID: " + estudiante.getId());
                return true;
            }
            return false;
            
        } catch (SQLException e) {
            System.err.println("✗ Error al registrar estudiante: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * READ ALL - Consultar todos los estudiantes
     * 
     * @return Lista de todos los estudiantes
     */
    @Override
    public List<Estudiante> readAll() {
        List<Estudiante> estudiantes = new ArrayList<>();
        String sql = "SELECT id, nombre_completo, correo_electronico, carrera FROM estudiantes ORDER BY id";
        
        try (Statement statement = conexion.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            
            while (resultSet.next()) {
                Estudiante estudiante = new Estudiante(
                    resultSet.getInt("id"),
                    resultSet.getString("nombre_completo"),
                    resultSet.getString("correo_electronico"),
                    resultSet.getString("carrera")
                );
                estudiantes.add(estudiante);
            }
            System.out.println("✓ Se encontraron " + estudiantes.size() + " estudiantes");
            
        } catch (SQLException e) {
            System.err.println("✗ Error al consultar estudiantes: " + e.getMessage());
        }
        
        return estudiantes;
    }
    
    /**
     * READ BY ID - Consultar un estudiante por su ID
     * 
     * @param id ID del estudiante a buscar
     * @return Objeto Estudiante si se encuentra, null en caso contrario
     */
    @Override
    public Estudiante readById(int id) {
        String sql = "SELECT id, nombre_completo, correo_electronico, carrera FROM estudiantes WHERE id = ?";
        
        try (PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, id);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Estudiante estudiante = new Estudiante(
                        resultSet.getInt("id"),
                        resultSet.getString("nombre_completo"),
                        resultSet.getString("correo_electronico"),
                        resultSet.getString("carrera")
                    );
                    System.out.println("✓ Estudiante encontrado: " + estudiante.getNombreCompleto());
                    return estudiante;
                }
            }
            
        } catch (SQLException e) {
            System.err.println("✗ Error al buscar estudiante por ID: " + e.getMessage());
        }
        
        System.out.println("✗ No se encontró estudiante con ID: " + id);
        return null;
    }
    
    /**
     * UPDATE - Actualizar los datos de un estudiante
     * 
     * @param estudiante Objeto estudiante con los datos actualizados
     * @return true si se actualizó correctamente, false en caso contrario
     */
    @Override
    public boolean update(Estudiante estudiante) {
        String sql = "UPDATE estudiantes SET nombre_completo = ?, correo_electronico = ?, carrera = ? WHERE id = ?";
        
        try (PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setString(1, estudiante.getNombreCompleto());
            statement.setString(2, estudiante.getCorreoElectronico());
            statement.setString(3, estudiante.getCarrera());
            statement.setInt(4, estudiante.getId());
            
            int filasAfectadas = statement.executeUpdate();
            
            if (filasAfectadas > 0) {
                System.out.println("✓ Estudiante actualizado: ID " + estudiante.getId());
                return true;
            }
            System.out.println("✗ No se encontró estudiante con ID: " + estudiante.getId());
            return false;
            
        } catch (SQLException e) {
            System.err.println("✗ Error al actualizar estudiante: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * DELETE - Eliminar un estudiante por su ID
     * 
     * @param id ID del estudiante a eliminar
     * @return true si se eliminó correctamente, false en caso contrario
     */
    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM estudiantes WHERE id = ?";
        
        try (PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, id);
            
            int filasAfectadas = statement.executeUpdate();
            
            if (filasAfectadas > 0) {
                System.out.println("✓ Estudiante eliminado: ID " + id);
                return true;
            }
            System.out.println("✗ No se encontró estudiante con ID: " + id);
            return false;
            
        } catch (SQLException e) {
            System.err.println("✗ Error al eliminar estudiante: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Método para cerrar la conexión (debe llamarse cuando ya no se necesite el DAO)
     */
    @Override
    public void cerrarConexion() {
        ConexionBD.cerrarConexion();
    }
}
