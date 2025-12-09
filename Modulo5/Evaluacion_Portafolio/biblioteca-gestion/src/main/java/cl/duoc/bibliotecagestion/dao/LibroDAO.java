package cl.duoc.bibliotecagestion.dao;

import cl.duoc.bibliotecagestion.model.Libro;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para la entidad Libro.
 * Implementa operaciones CRUD utilizando JDBC.
 */
public class LibroDAO implements ILibroDAO {
    
    private Connection conexion;
    
    /**
     * Constructor que inicializa la conexión a la base de datos
     */
    public LibroDAO() {
        try {
            this.conexion = ConexionBD.obtenerConexion();
        } catch (SQLException e) {
            System.err.println("✗ Error al inicializar LibroDAO: " + e.getMessage());
        }
    }
    
    /**
     * CREATE - Registrar un nuevo libro en la base de datos
     */
    @Override
    public boolean create(Libro libro) {
        String sql = "INSERT INTO libros (titulo, autor, isbn, disponible) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement statement = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, libro.getTitulo());
            statement.setString(2, libro.getAutor());
            statement.setString(3, libro.getIsbn());
            statement.setBoolean(4, libro.getDisponible());
            
            int filasAfectadas = statement.executeUpdate();
            
            if (filasAfectadas > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    libro.setId(generatedKeys.getInt(1));
                    System.out.println("✓ Libro registrado con ID: " + libro.getId());
                }
                return true;
            }
            return false;
            
        } catch (SQLException e) {
            System.err.println("✗ Error al registrar libro: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * READ ALL - Consultar todos los libros
     */
    @Override
    public List<Libro> readAll() {
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT id, titulo, autor, isbn, disponible FROM libros ORDER BY titulo";
        
        try (Statement statement = conexion.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            
            while (resultSet.next()) {
                Libro libro = new Libro(
                    resultSet.getInt("id"),
                    resultSet.getString("titulo"),
                    resultSet.getString("autor"),
                    resultSet.getString("isbn"),
                    resultSet.getBoolean("disponible")
                );
                libros.add(libro);
            }
            
            System.out.println("✓ Se obtuvieron " + libros.size() + " libros");
            
        } catch (SQLException e) {
            System.err.println("✗ Error al consultar libros: " + e.getMessage());
        }
        
        return libros;
    }
    
    /**
     * READ DISPONIBLES - Consultar solo libros disponibles
     */
    @Override
    public List<Libro> readDisponibles() {
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT id, titulo, autor, isbn, disponible FROM libros WHERE disponible = TRUE ORDER BY titulo";
        
        try (Statement statement = conexion.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            
            while (resultSet.next()) {
                Libro libro = new Libro(
                    resultSet.getInt("id"),
                    resultSet.getString("titulo"),
                    resultSet.getString("autor"),
                    resultSet.getString("isbn"),
                    resultSet.getBoolean("disponible")
                );
                libros.add(libro);
            }
            
            System.out.println("✓ Se obtuvieron " + libros.size() + " libros disponibles");
            
        } catch (SQLException e) {
            System.err.println("✗ Error al consultar libros disponibles: " + e.getMessage());
        }
        
        return libros;
    }
    
    /**
     * READ BY ID - Consultar un libro por su ID
     */
    @Override
    public Libro readById(int id) {
        String sql = "SELECT id, titulo, autor, isbn, disponible FROM libros WHERE id = ?";
        
        try (PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, id);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Libro libro = new Libro(
                        resultSet.getInt("id"),
                        resultSet.getString("titulo"),
                        resultSet.getString("autor"),
                        resultSet.getString("isbn"),
                        resultSet.getBoolean("disponible")
                    );
                    System.out.println("✓ Libro encontrado: " + libro.getTitulo());
                    return libro;
                }
            }
            
        } catch (SQLException e) {
            System.err.println("✗ Error al buscar libro: " + e.getMessage());
        }
        
        System.out.println("✗ No se encontró libro con ID: " + id);
        return null;
    }
    
    /**
     * UPDATE - Actualizar los datos de un libro
     */
    @Override
    public boolean update(Libro libro) {
        String sql = "UPDATE libros SET titulo = ?, autor = ?, isbn = ?, disponible = ? WHERE id = ?";
        
        try (PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setString(1, libro.getTitulo());
            statement.setString(2, libro.getAutor());
            statement.setString(3, libro.getIsbn());
            statement.setBoolean(4, libro.getDisponible());
            statement.setInt(5, libro.getId());
            
            int filasAfectadas = statement.executeUpdate();
            
            if (filasAfectadas > 0) {
                System.out.println("✓ Libro actualizado: " + libro.getTitulo());
                return true;
            }
            
            System.out.println("✗ No se pudo actualizar el libro con ID: " + libro.getId());
            return false;
            
        } catch (SQLException e) {
            System.err.println("✗ Error al actualizar libro: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * DELETE - Eliminar un libro por su ID
     */
    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM libros WHERE id = ?";
        
        try (PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, id);
            
            int filasAfectadas = statement.executeUpdate();
            
            if (filasAfectadas > 0) {
                System.out.println("✓ Libro eliminado con ID: " + id);
                return true;
            }
            
            System.out.println("✗ No se encontró libro con ID: " + id);
            return false;
            
        } catch (SQLException e) {
            System.err.println("✗ Error al eliminar libro: " + e.getMessage());
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
