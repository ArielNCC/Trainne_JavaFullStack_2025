package cl.duoc.bibliotecagestion.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Clase para encapsular la lógica de conexión a la base de datos
 * y reutilizarla en toda la aplicación.
 */
public class ConexionBD {
    
    // Variable para almacenar la conexión única (patrón Singleton)
    private static Connection conexion = null;
    
    /**
     * Método para obtener una conexión a la base de datos.
     * Implementa el patrón Singleton para reutilizar la misma conexión.
     * 
     * @return Conexión a la base de datos
     * @throws SQLException Si hay un error al conectar
     */
    public static Connection obtenerConexion() throws SQLException {
        try {
            if (conexion == null || conexion.isClosed()) {
                // Cargar propiedades
                Properties props = new Properties();
                try (InputStream input = ConexionBD.class.getClassLoader().getResourceAsStream("database.properties")) {
                    if (input == null) {
                        throw new IOException("No se pudo encontrar el archivo database.properties");
                    }
                    props.load(input);
                } catch (IOException ex) {
                    System.err.println("✗ Error al cargar configuración: " + ex.getMessage());
                    throw new SQLException("Error al cargar configuración de base de datos", ex);
                }

                String driver = props.getProperty("db.driver");
                String url = props.getProperty("db.url");
                String usuario = props.getProperty("db.username");
                String password = props.getProperty("db.password");

                // Cargar el driver de MySQL
                Class.forName(driver);
                conexion = DriverManager.getConnection(url, usuario, password);
                System.out.println("✓ Conexión exitosa a la base de datos");
            }
            return conexion;
        } catch (ClassNotFoundException e) {
            System.err.println("✗ Error: No se encontró el driver de MySQL");
            throw new SQLException("Driver de MySQL no encontrado", e);
        } catch (SQLException e) {
            System.err.println("✗ Error al conectar a la base de datos: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Método para cerrar la conexión a la base de datos.
     */
    public static void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                System.out.println("✓ Conexión cerrada correctamente");
            }
        } catch (SQLException e) {
            System.err.println("✗ Error al cerrar conexión: " + e.getMessage());
        }
    }
    
    /**
     * Método para probar la conexión a la base de datos
     * 
     * @return true si la conexión es exitosa, false en caso contrario
     */
    public static boolean probarConexion() {
        try {
            Connection conn = obtenerConexion();
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }
}
