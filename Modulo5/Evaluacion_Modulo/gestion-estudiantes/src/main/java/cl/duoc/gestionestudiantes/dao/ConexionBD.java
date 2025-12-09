package cl.duoc.gestionestudiantes.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase para encapsular la lógica de conexión a la base de datos
 * y reutilizarla en toda la aplicación.
 */
public class ConexionBD {
    
    // Configuración de la base de datos
    private static final String URL = "jdbc:mysql://localhost:TUPUERTO/TUBASEDEDATOS?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String USUARIO = "TUSUARIO";
    private static final String PASSWORD = "TUPASSWORD";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    
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
            // Cargar el driver de MySQL
            if (conexion == null || conexion.isClosed()) {
                Class.forName(DRIVER);
                conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
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
